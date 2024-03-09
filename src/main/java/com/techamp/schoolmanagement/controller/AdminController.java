package com.techamp.schoolmanagement.controller;


import com.techamp.schoolmanagement.model.Class;
import com.techamp.schoolmanagement.model.Course;
import com.techamp.schoolmanagement.model.Person;
import com.techamp.schoolmanagement.repositories.ClassRepository;
import com.techamp.schoolmanagement.repositories.CoursesRespository;
import com.techamp.schoolmanagement.repositories.PersonRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping(value = "/admin")
public class AdminController {
	
	@Autowired
	ClassRepository classRepository;
	
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	CoursesRespository coursesRepository;
	
	@GetMapping("/displayCourses")
	public ModelAndView displayCourses() {
		List<Course> courses = coursesRepository.findAll();
		ModelAndView adminCourses = new ModelAndView("courses_secure.html");
		adminCourses.addObject("courses", courses);
		adminCourses.addObject("course", new Course());
		return adminCourses;
	}
	
	@PostMapping("/addNewCourse")
	public ModelAndView addNewCourse(@ModelAttribute("course") Course course) {
		coursesRepository.save(course);
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayCourses");
		return modelAndView;
	}
	
	@GetMapping("/displayClass")
	public ModelAndView displayClasses() {
		List<Class> classes = classRepository.findAll();
		ModelAndView modelAndView = new ModelAndView("classes.html");
		modelAndView.addObject("classes", classes);
		modelAndView.addObject("classe", new Class());
		return modelAndView;
	}
	
	@GetMapping("/displayStudents")
	public ModelAndView displayStudents(@RequestParam("classId") Long classId, HttpSession session,
																			@RequestParam(value = "error", required = false) String error) {
		String errorMessage = null;
		ModelAndView studentsView = new ModelAndView("students.html");
		Optional<Class> classe = classRepository.findById(classId);
		studentsView.addObject("classe", classe.get());
		studentsView.addObject("student", new Person());
		session.setAttribute("TechClass", classe.get());
		if (error != null) {
			errorMessage = "Invalid Email entered!!";
			studentsView.addObject("errorMessage", errorMessage);
		}
		return studentsView;
	}
	
	@PostMapping("/addStudent")
	public ModelAndView addStudent(Model model, @ModelAttribute("student") Person student, HttpSession httpSession) {
		ModelAndView modelAndView = new ModelAndView();
		Class classe = (Class) httpSession.getAttribute("TechClass");
		Person personEntity = personRepository.readByEmail(student.getEmail());
		if(personEntity==null || !(personEntity.getPersonId()>0)) {
			modelAndView.setViewName("redirect:/admin/displayStudents?classId="+ classe.getClassId()
			+"&error=true");
			return modelAndView;
		}
		try {
			personEntity.setClasse(classe);
			personRepository.save(personEntity);
			classe.getPersons().add(personEntity);
			classRepository.save(classe);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		modelAndView.setViewName("redirect:/admin/displayStudents?classId="+ classe.getClassId());
		return modelAndView;
	}
	
	@GetMapping("/viewStudents")
	public ModelAndView viewStudents(@RequestParam("courseID") Long courseId, HttpSession httpSession, Model model,
																	 @RequestParam(value = "error", required = false) String error) {
		String errorMessage = null;
		ModelAndView studentsCourseView = new ModelAndView("course_students.html");
		Optional<Course> course = coursesRepository.findById(courseId);
		studentsCourseView.addObject("courses", course.get());
		studentsCourseView.addObject("person", new Person());
		httpSession.setAttribute("courses",course.get());
		if(error != null) {
			errorMessage = "Invalid Email entered!!";
			studentsCourseView.addObject("errorMessage", errorMessage);
		}
		return studentsCourseView;
	}
	
	@DeleteMapping("deleteStudentFromCourse")
	public ModelAndView deleteStudentFromCourse(@RequestParam("personId") Long personId,
																							Model model, HttpSession httpSession) {
		String errorMessage = null;
		Course course = (Course) httpSession.getAttribute("courses");
		if  (!(course.getPersons().stream().map(Person::getPersonId).toList()).contains(personId)) {
			errorMessage = "This student does not belong to this course";
			return new ModelAndView("redirect:/viewStudents?courseID=" + course.getCourseId().toString());
		}
		
		Optional<Person> person = personRepository.findById(personId);
		person.get().getCourses().remove(course);
		personRepository.save(person.get());
		Set<Person> newListOfStudents = course.getPersons().stream().filter( it -> !Objects.equals(it.getPersonId(), personId)).collect(Collectors.toSet());
		course.setPersons(newListOfStudents);
		coursesRepository.save(course);
		
		return new ModelAndView("redirect:/admin/viewStudents?id="+course.getCourseId());
	}
	
	@DeleteMapping("/deleteStudent")
	public ModelAndView deleteStudent(Model model, @RequestParam("studentId") Long studentId, HttpSession session) {
		String errorMessage = null;
		Class classe = (Class) session.getAttribute("TechClass");
		Optional<Person> student = personRepository.findById(studentId);

		student.get().setClasse(null);
		classe.getPersons().remove(student.get());
		Class c = classRepository.save(classe);
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId=" + c.getClassId());
		
		return modelAndView;
	}
	
	@PostMapping("/addNewClass")
	public ModelAndView addNewClass(Model model, @Valid @ModelAttribute("classe") Class classe) {
		classRepository.save(classe);
		ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClass");
		return modelAndView;
	}
}
