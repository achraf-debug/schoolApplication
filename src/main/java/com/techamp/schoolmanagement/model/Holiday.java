package com.techamp.schoolmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Holiday {
	private String day;
	private String reason;
	private Type type;
	
	
	public enum Type {
		FEDERAL, FESTIVAL
	}
	
}
