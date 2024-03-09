package com.techamp.schoolmanagement.model;

import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Setter
@Getter
@Entity
@Table(name = "holidays")
public class Holiday {
	
	@Id
	private String day;
	private String reason;
	@Enumerated(EnumType.STRING)
	private Type type;
	
	public enum Type {
		FEDERAL, FESTIVAL
	}
}
