package com.techamp.schoolmanagement.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "course_id")
	private Long courseId;
	
	@NotBlank(message="Course name must not be blank")
	@Size(min=5, message="Course name must be at least 5 characters long")
	private String name;
	
	@NotBlank(message="Fees must not be blank")
	@Size(min=5, message="Fees must be at least 5 characters long")
	private String fees;
	
	@ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
	private Set<Person> persons = new HashSet<>();
	
}
