package com.example.csvbatch.config;

import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.example.csvbatch.dto.EmployeeCsvRecord;

@Configuration
public class EmployeeReaderConfig {

	@Bean
	FlatFileItemReader<EmployeeCsvRecord> employeeReader() {

		return new FlatFileItemReaderBuilder<EmployeeCsvRecord>().name("employeeReader")
				.resource(new ClassPathResource("employees_100.csv")).linesToSkip(1).delimited().delimiter(",")
				.names("employeeId", "name", "email", "salary").targetType(EmployeeCsvRecord.class).build();
	}
}
