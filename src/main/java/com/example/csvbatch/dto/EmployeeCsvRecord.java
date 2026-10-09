package com.example.csvbatch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCsvRecord {

	private String employeeId;

	private String name;

	private String email;

	private String salary;
}
