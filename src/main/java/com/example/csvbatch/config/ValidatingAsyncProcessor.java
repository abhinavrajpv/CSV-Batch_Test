package com.example.csvbatch.config;

import java.util.concurrent.Future;

import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.stereotype.Component;

import com.example.csvbatch.dto.EmployeeCsvRecord;
import com.example.csvbatch.entity.Employee;
import com.example.csvbatch.processor.EmployeeItemProcessor;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ValidatingAsyncProcessor implements ItemProcessor<EmployeeCsvRecord, Future<Employee>> {

	private final EmployeeItemProcessor validator;
	private final AsyncItemProcessor<EmployeeCsvRecord, Employee> async;

	@Override
	public Future<Employee> process(EmployeeCsvRecord record) throws Exception {
		validator.validate(record); 
		return async.process(record);
	}
}