package com.example.csvbatch.repository;
import com.example.csvbatch.entity.EmployeeError;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeErrorRepository
        extends JpaRepository<EmployeeError, Long> {
	boolean existsByEmployeeId(String employeeId);
}
