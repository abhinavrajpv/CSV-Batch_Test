package com.example.csvbatch.config;

import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import com.example.csvbatch.dto.EmployeeCsvRecord;

@Configuration
public class EmployeeReaderConfig {

	@Value("${employee.csv.path}")
	private String filePath;

	@Bean
	FlatFileItemReader<EmployeeCsvRecord> employeeReader() {

		return new FlatFileItemReaderBuilder<EmployeeCsvRecord>().name("employeeReader")
				.resource(new FileSystemResource(filePath)).linesToSkip(1).delimited().delimiter(",")
				.names("employeeId", "name", "email", "salary").targetType(EmployeeCsvRecord.class).build();
	}
}
