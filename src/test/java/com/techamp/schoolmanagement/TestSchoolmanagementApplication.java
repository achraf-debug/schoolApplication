package com.techamp.schoolmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestSchoolmanagementApplication {
	
	public static void main(String[] args) {
		SpringApplication.from(SchoolmanagementApplication::main).with(TestSchoolmanagementApplication.class).run(args);
	}
	
}
