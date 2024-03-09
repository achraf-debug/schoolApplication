package com.techamp.schoolmanagement.service;

import com.techamp.schoolmanagement.constants.Constants;
import com.techamp.schoolmanagement.model.Person;
import com.techamp.schoolmanagement.model.Role;
import com.techamp.schoolmanagement.repositories.PersonRepository;
import com.techamp.schoolmanagement.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private RoleRepository rolesRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public boolean createNewPerson(Person person) {
		boolean isSaved = false;
		Role role = rolesRepository.getRoleByRoleName(Constants.STUDENT_ROLE);
		person.setRole(role);
		person.setPwd(passwordEncoder.encode(person.getPwd()));
		person = personRepository.save(person);
		if (null != person && person.getPersonId() > 0)
		{
			isSaved = true;
		}
		return isSaved;
	}
}
