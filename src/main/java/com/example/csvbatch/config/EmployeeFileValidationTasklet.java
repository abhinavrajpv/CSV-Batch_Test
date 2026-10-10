package com.example.csvbatch.config;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmployeeFileValidationTasklet implements Tasklet {

	@Value("${employee.csv.path}")
	private String filePath;

	@Value("${employee.csv.max-size}")
	private long maxSize;

	@Value("${employee.csv.headers}")
	private String expectedHeaders;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		Path path = Path.of(filePath);
		List<String> errors = new ArrayList<>();

		if (!Files.isRegularFile(path) || !Files.isReadable(path)) {
			errors.add("CSV file does not exist or is not readable");
		} else {

			if (!path.getFileName().toString().toLowerCase(java.util.Locale.ROOT).endsWith(".csv")) {
				errors.add("Only CSV files are allowed");
			}

			long fileSize = Files.size(path);

			if (fileSize > maxSize) {
				errors.add("CSV file exceeds the maximum allowed size");
			}

			if (fileSize == 0) {
				errors.add("CSV file is empty");
			} else if (fileSize <= maxSize) {

				try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {

					String headerLine = reader.readLine();

					if (headerLine == null || headerLine.isBlank()) {
						errors.add("CSV file is empty or headers are missing");
					} else {
						List<String> actualHeaders = Arrays.stream(headerLine.replace("\uFEFF", "").split(",", -1))
								.map(String::trim).toList();

						List<String> requiredHeaders = Arrays.stream(expectedHeaders.split(",", -1)).map(String::trim)
								.toList();

						if (!actualHeaders.equals(requiredHeaders)) {
							errors.add("Invalid CSV headers. Expected: " + requiredHeaders);
						}
					}
				} catch (java.io.IOException e) {
					errors.add("Unable to read CSV file: " + e.getMessage());
				}
			}
		}

		if (!errors.isEmpty()) {
			throw new IllegalArgumentException(String.join("; ", errors));
		}

		System.out.println("CSV file validation successful");

		return RepeatStatus.FINISHED;
	}
}