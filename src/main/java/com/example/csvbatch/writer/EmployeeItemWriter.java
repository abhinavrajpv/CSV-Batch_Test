package com.example.csvbatch.writer;

import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.example.csvbatch.entity.Employee;
import com.example.csvbatch.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmployeeItemWriter implements ItemWriter<Employee> {

	private final EmployeeRepository employeeRepository;

	@Override
	public void write(Chunk<? extends Employee> chunk) {
		employeeRepository.saveAll(chunk.getItems());
	}
}
