package com.example.csvbatch.config;

import java.util.concurrent.Future;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.csvbatch.dto.EmployeeCsvRecord;
import com.example.csvbatch.entity.Employee;
import com.example.csvbatch.exception.EmployeeValidationException;
import com.example.csvbatch.listener.EmployeeSkipListener;

@Configuration
public class BatchConfig {

	@Bean
	public Step employeeStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
			FlatFileItemReader<EmployeeCsvRecord> employeeReader,
			AsyncItemProcessor<EmployeeCsvRecord, Employee> processor, AsyncItemWriter<Employee> writer,
			EmployeeSkipListener skipListener, ValidatingAsyncProcessor validatingAsyncProcessor) {

		return new StepBuilder("employeeStep", jobRepository)
				.<EmployeeCsvRecord, Future<Employee>>chunk(10, transactionManager).reader(employeeReader)
				.processor(validatingAsyncProcessor) 
				.writer(writer).faultTolerant().skip(EmployeeValidationException.class)
				.noRollback(EmployeeValidationException.class)
				.skipLimit(20).listener(skipListener).build();
	}

	@Bean
	public Step fileValidationStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
			EmployeeFileValidationTasklet tasklet) {

		return new StepBuilder("fileValidationStep", jobRepository).tasklet(tasklet, transactionManager)
				.allowStartIfComplete(true).build();
	}

	@Bean
	public Job employeeJob(JobRepository jobRepository, Step fileValidationStep, Step employeeStep) {

		return new JobBuilder("employeeJob", jobRepository).start(fileValidationStep).next(employeeStep).build();
	}
}
