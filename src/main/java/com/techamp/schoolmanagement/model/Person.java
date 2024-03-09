package com.techamp.schoolmanagement.model;

import com.techamp.schoolmanagement.annotations.FieldsValueMatch;
import com.techamp.schoolmanagement.annotations.PasswordValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity(name = "person")
@FieldsValueMatch.List({
		@FieldsValueMatch(
				field = "pwd",
				fieldMatch ="confirmPwd",
				message = "Passwords do not match !"),
		@FieldsValueMatch(
			field = "email",
		  fieldMatch = "confirmEmail",
		  message = "Email adresses do not match !"
		)
})
public class Person extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "person_id")
	private Long personId;

	@NotBlank(message = "Name must not be empty")
	@Size(min = 3, message = "Name must have at least 3 letters !")
	private String name;
	
	@NotBlank(message = "Mobile number must not be empty")
	@Pattern(regexp = "(^$|[0-9]{8})", message = "Phone number must be 8 digits")
	private String mobileNumber;
	
	@NotBlank(message = "Email must not be empty")
	@Email
	private String email;
	@NotBlank(message = "Email must not be empty")
	@Email
	@Transient
	private String confirmEmail;
	
	@NotBlank(message="Password must not be blank")
	@Size(min=5, message="Password must be at least 5 characters long")
	@PasswordValidator
	private String pwd;
	
	@NotBlank(message="Confirm Password must not be blank")
	@Size(min=5, message="Confirm Password must be at least 5 characters long")
	@Transient
	private String confirmPwd;
	
	@ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST, targetEntity = Role.class)
	@JoinColumn(name = "role_id", referencedColumnName = "roleId",nullable = false)
	private Role role;
	
	@ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL, targetEntity = Address.class)
	@JoinColumn(name = "address_id", referencedColumnName = "addressId",nullable = true)
	private Address address;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Class.class)
	@JoinColumn(name = "class_id", referencedColumnName = "class_id",nullable = true)
	private Class classe;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, targetEntity = Course.class)
	private Set<Course> courses = new HashSet<>();;
}
