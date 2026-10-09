package com.example.csvbatch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeError {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long errorId;

	private String employeeId;

	private String name;

	private String email;

	private String salary;

	private String errorReason;
}
