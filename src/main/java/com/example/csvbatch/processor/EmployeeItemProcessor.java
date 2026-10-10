
package com.example.csvbatch.processor;

import java.math.BigDecimal;

import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.csvbatch.dto.EmployeeCsvRecord;
import com.example.csvbatch.entity.Employee;
import com.example.csvbatch.exception.EmployeeValidationException;

@Component
public class EmployeeItemProcessor implements ItemProcessor<EmployeeCsvRecord, Employee> {

	public void validate(EmployeeCsvRecord record) {
		if (record.getName() == null || record.getName().isBlank())
			throw new EmployeeValidationException("Name is missing");
		if (record.getEmail() == null || !record.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
			throw new EmployeeValidationException("Invalid email");
		try {
			Long.parseLong(record.getEmployeeId());
		} catch (NumberFormatException e) {
			throw new EmployeeValidationException("Invalid employee ID");
		}
		try {
			if (new BigDecimal(record.getSalary()).signum() <= 0)
				throw new EmployeeValidationException("Salary must be positive");
		} catch (NumberFormatException e) {
			throw new EmployeeValidationException("Salary must be numeric");
		}
	}

	@Override
	public Employee process(EmployeeCsvRecord record) {
		return new Employee(null, record.getEmployeeId(), record.getName(), record.getEmail(),
				new BigDecimal(record.getSalary()));
	}
}