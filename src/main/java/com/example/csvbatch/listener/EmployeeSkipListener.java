package com.example.csvbatch.listener;

import org.springframework.batch.core.listener.SkipListener;
import org.springframework.stereotype.Component;

import com.example.csvbatch.dto.EmployeeCsvRecord;
import com.example.csvbatch.entity.Employee;
import com.example.csvbatch.entity.EmployeeError;
import com.example.csvbatch.repository.EmployeeErrorRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmployeeSkipListener implements SkipListener<EmployeeCsvRecord, Employee> {

	private final EmployeeErrorRepository errorRepository;

	@Override
	public void onSkipInProcess(EmployeeCsvRecord record, Throwable throwable) {
		EmployeeError error = new EmployeeError(null, record.getEmployeeId(), record.getName(), record.getEmail(),
				record.getSalary(), throwable.getMessage());
		errorRepository.save(error);
	}
}