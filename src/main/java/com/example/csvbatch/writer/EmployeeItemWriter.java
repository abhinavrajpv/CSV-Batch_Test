/*
 * package com.example.csvbatch.writer;
 * 
 * import java.util.ArrayList; import java.util.HashSet; import java.util.List;
 * import java.util.Set; import java.util.stream.Collectors;
 * 
 * import org.springframework.batch.infrastructure.item.Chunk; import
 * org.springframework.batch.infrastructure.item.ItemWriter; import
 * org.springframework.stereotype.Component;
 * 
 * import com.example.csvbatch.entity.Employee; import
 * com.example.csvbatch.repository.EmployeeRepository;
 * 
 * import lombok.RequiredArgsConstructor;
 * 
 * @Component
 * 
 * @RequiredArgsConstructor public class EmployeeItemWriter implements
 * ItemWriter<Employee> {
 * 
 * private final EmployeeRepository employeeRepository;
 * 
 * @Override public void write(Chunk<? extends Employee> chunk) { if
 * (chunk.isEmpty()) { return; }
 * 
 * Set<String> employeeIds =
 * chunk.getItems().stream().map(Employee::getEmployeeId).collect(Collectors.
 * toSet());
 * 
 * Set<String> emails =
 * chunk.getItems().stream().map(Employee::getEmail).collect(Collectors.toSet())
 * ;
 * 
 * Set<String> existingIds = employeeIds.isEmpty() ? new HashSet<>() : new
 * HashSet<>(employeeRepository.findExistingEmployeeIds(employeeIds));
 * 
 * Set<String> existingEmails = emails.isEmpty() ? new HashSet<>() : new
 * HashSet<>(employeeRepository.findExistingEmails(emails));
 * 
 * List<Employee> validEmployees = new ArrayList<>();
 * 
 * for (Employee employee : chunk.getItems()) {
 * 
 * String employeeId = employee.getEmployeeId(); String email =
 * employee.getEmail();
 * 
 * if (existingIds.contains(employeeId) || existingEmails.contains(email)) {
 * 
 * System.out.println("Duplicate skipped: employeeId=" + employeeId + ", email="
 * + email);
 * 
 * continue; }
 * 
 * existingIds.add(employeeId); existingEmails.add(email);
 * 
 * validEmployees.add(employee); }
 * 
 * if (!validEmployees.isEmpty()) { employeeRepository.saveAll(validEmployees);
 * } } }
 */