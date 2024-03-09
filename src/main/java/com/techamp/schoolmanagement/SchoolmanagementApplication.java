package com.techamp.schoolmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.techamp.schoolmanagement.repositories")
@EntityScan("com.techamp.schoolmanagement.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class SchoolmanagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(SchoolmanagementApplication.class, args);
	}
}
