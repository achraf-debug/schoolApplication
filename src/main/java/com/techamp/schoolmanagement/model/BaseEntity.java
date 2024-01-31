package com.techamp.schoolmanagement.model;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {

	private String createdBy;
	private LocalDateTime createdAt;
	private String updatedBy;
	private LocalDateTime updatedAt;
}
