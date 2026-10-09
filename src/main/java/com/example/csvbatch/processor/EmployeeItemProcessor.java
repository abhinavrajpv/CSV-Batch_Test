package com.example.csvbatch.processor;

import java.math.BigDecimal;

import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.csvbatch.dto.EmployeeCsvRecord;
import com.example.csvbatch.entity.Employee;
import com.example.csvbatch.exception.EmployeeValidationException;

@Component
public class EmployeeItemProcessor implements ItemProcessor<EmployeeCsvRecord, Employee> {

	@Override
	public Employee process(EmployeeCsvRecord record) {

		if (record.getName() == null || record.getName().isBlank()) {
			throw new EmployeeValidationException("Name is missing");
		}

		if (record.getEmail() == null || !record.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			throw new EmployeeValidationException("Invalid email");
		}

		long id;
		try {
			id = Long.parseLong(record.getEmployeeId());
		} catch (NumberFormatException e) {
			throw new EmployeeValidationException("Invalid employee ID");
		}

		BigDecimal salary;
		try {
			salary = new BigDecimal(record.getSalary());
		} catch (NumberFormatException e) {
			throw new EmployeeValidationException("Salary must be numeric");
		}

		if (salary.signum() <= 0) {
			throw new EmployeeValidationException("Salary must be positive");
		}

		return new Employee(id, record.getName(), record.getEmail(), salary);
	}
}