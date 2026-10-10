
package com.example.csvbatch.listener;

import java.util.concurrent.Future;

import org.springframework.batch.core.listener.SkipListener;
import org.springframework.stereotype.Component;

import com.example.csvbatch.dto.EmployeeCsvRecord;
import com.example.csvbatch.entity.Employee;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EmployeeSkipListener implements SkipListener<EmployeeCsvRecord, Future<Employee>> {

	@Override
	public void onSkipInProcess(EmployeeCsvRecord record, Throwable throwable) {

		log.warn("Employee skipped during processing. ID: {}, Reason: {}", record.getEmployeeId(),
				throwable.getMessage());
	}

	@Override
	public void onSkipInWrite(Future<Employee> item, Throwable throwable) {

		log.warn("Async employee processing failed and was skipped during writing. Reason: {}", throwable.getMessage());
	}
}
