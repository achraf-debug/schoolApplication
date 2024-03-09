package com.techamp.schoolmanagement.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "contact_msg")
public class ContactMessage extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "contact_id")
	private Long contactId;
	
	@NotBlank(message = "Name must not be blank")
	@Size(min = 3, message = "Name must be at least 3 characters long")
	private String name;
	@NotBlank
	@Pattern(regexp = "(^$|[0-9]{8})", message = "Phone number must be 8 digits")
	private String mobileNum;
	@NotBlank(message = "Email must not be blank")
	@Email(message = "Email must be on this format: x@y.com")
	private String email;
	
	@NotBlank(message = "Subject must not be blank")
	@Size(min = 5, message = "subject must be at least 5 characters long")
	private String subject;
	
	@NotBlank
	@Size(min = 5, message = "Message must be at least 5 characters long")
	private String message;
	
	private String status;
}
