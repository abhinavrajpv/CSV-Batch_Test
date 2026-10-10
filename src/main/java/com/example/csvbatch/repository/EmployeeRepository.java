package com.example.csvbatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.csvbatch.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	/*
	 * @Query(""" select e.employeeId from Employee e where e.employeeId in
	 * :employeeIds """) Set<String> findExistingEmployeeIds(@Param("employeeIds")
	 * Collection<String> employeeIds);
	 * 
	 * @Query(""" select e.email from Employee e where e.email in :emails """)
	 * Set<String> findExistingEmails(@Param("emails") Collection<String> emails);
	 */
}
