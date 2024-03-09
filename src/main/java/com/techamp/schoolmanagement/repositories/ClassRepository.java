package com.techamp.schoolmanagement.repositories;

import com.techamp.schoolmanagement.model.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
}
