package com.example.csvbatch.config;

import javax.sql.DataSource;

import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.csvbatch.entity.Employee;

@Configuration
public class EmployeeJdbcWriterConfig {

	@Bean
	public JdbcBatchItemWriter<Employee> employeeJdbcWriter(DataSource dataSource) {

		return new JdbcBatchItemWriterBuilder<Employee>().dataSource(dataSource).sql("""
				INSERT INTO employee
				    (employee_id, name, email, salary)
				VALUES
				    (:employeeId, :name, :email, :salary)
				ON CONFLICT DO NOTHING
				""").beanMapped().assertUpdates(false).build();
	}
}
