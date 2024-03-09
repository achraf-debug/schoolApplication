package com.techamp.schoolmanagement.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Entity(name = "class")
public class Class extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "class_id")
	private Long classId;
	
	@NotBlank(message = "Name must not be empty")
	@Size(min = 3, message = "Name must have at least 3 letters !")
	private String name;
	
	@OneToMany(mappedBy = "classe", fetch = FetchType.LAZY,
	targetEntity = Person.class)
	private Set<Person> persons = new HashSet<>();
}
