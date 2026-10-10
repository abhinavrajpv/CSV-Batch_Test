
package com.example.csvbatch.listener;

import java.util.concurrent.Future;

import org.springframework.batch.core.listener.SkipListener;
import org.springframework.stereotype.Component;

import com.example.csvbatch.dto.EmployeeCsvRecord;
import com.example.csvbatch.entity.Employee;
import com.example.csvbatch.entity.EmployeeError;
import com.example.csvbatch.repository.EmployeeErrorRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmployeeSkipListener implements SkipListener<EmployeeCsvRecord, Future<Employee>> {

	private final EmployeeErrorRepository errorRepository;

	@Override
	public void onSkipInProcess(EmployeeCsvRecord r, Throwable t) {
		log.warn("Skipped {}: {}", r.getEmployeeId(), t.getMessage());
		if (!errorRepository.existsByEmployeeId(r.getEmployeeId())) {
			errorRepository.save(new EmployeeError(null, r.getEmployeeId(), r.getName(), r.getEmail(), r.getSalary(),
					t.getMessage()));
		}
	}

	@Override
	public void onSkipInWrite(Future<Employee> item, Throwable t) {
		log.error("Unexpected write-phase failure: {}", t.getMessage());
	}
}
