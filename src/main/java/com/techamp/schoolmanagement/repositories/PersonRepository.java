package com.techamp.schoolmanagement.repositories;

import com.techamp.schoolmanagement.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
	Person readByEmail(String email);
}
