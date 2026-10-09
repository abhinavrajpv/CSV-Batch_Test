package com.example.csvbatch.config;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.csvbatch.dto.EmployeeCsvRecord;
import com.example.csvbatch.entity.Employee;
import com.example.csvbatch.exception.EmployeeValidationException;
import com.example.csvbatch.listener.EmployeeSkipListener;
import com.example.csvbatch.processor.EmployeeItemProcessor;
import com.example.csvbatch.writer.EmployeeItemWriter;

@Configuration
public class BatchConfig {

	@Bean
	public Step employeeStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
			FlatFileItemReader<EmployeeCsvRecord> employeeReader, EmployeeItemProcessor processor,
			EmployeeItemWriter writer, EmployeeSkipListener skipListener) {

		return new StepBuilder("employeeStep", jobRepository).<EmployeeCsvRecord, Employee>chunk(5, transactionManager)
				.reader(employeeReader).processor(processor).writer(writer).faultTolerant()
				.skip(EmployeeValidationException.class).skipLimit(20).listener(skipListener).build();
	}

	@Bean
	public Job employeeJob(JobRepository jobRepository, Step employeeStep) {

		return new JobBuilder("employeeJob", jobRepository).start(employeeStep).build();
	}
}
