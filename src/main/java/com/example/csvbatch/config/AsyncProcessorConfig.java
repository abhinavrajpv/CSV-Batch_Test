package com.example.csvbatch.config;

import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

import com.example.csvbatch.dto.EmployeeCsvRecord;
import com.example.csvbatch.entity.Employee;
import com.example.csvbatch.processor.EmployeeItemProcessor;

@Configuration
public class AsyncProcessorConfig {

    @Bean
    public AsyncItemProcessor<EmployeeCsvRecord, Employee> asyncEmployeeProcessor(
            EmployeeItemProcessor delegate,
            TaskExecutor processorTaskExecutor) {

    	AsyncItemProcessor<EmployeeCsvRecord, Employee> processor =
    	        new AsyncItemProcessor<EmployeeCsvRecord, Employee>(delegate);

        processor.setTaskExecutor(processorTaskExecutor);

        return processor;
    }

    @Bean
    public AsyncItemWriter<Employee> asyncEmployeeWriter(
            JdbcBatchItemWriter<Employee> employeeJdbcWriter) {

        AsyncItemWriter<Employee> writer = new AsyncItemWriter<Employee>(employeeJdbcWriter);


        return writer;
    }
}