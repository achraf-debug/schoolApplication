package com.techamp.schoolmanagement.repositories;

import com.techamp.schoolmanagement.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CoursesRespository extends JpaRepository<Course, Long> {

}
