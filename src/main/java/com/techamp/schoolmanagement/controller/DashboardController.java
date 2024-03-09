package com.techamp.schoolmanagement.controller;


import com.techamp.schoolmanagement.model.Person;
import com.techamp.schoolmanagement.repositories.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class DashboardController {
	
	@Autowired
	private PersonRepository personRepository;
	
	@GetMapping(value = "/dashboard")
	public String getDashboard(Model model, Authentication authentication, HttpSession httpSession) {
		Person person = personRepository.readByEmail(authentication.getName());
		if(person != null && person.getPersonId()>0) {
			httpSession.setAttribute("loggedPerson", person);
			model.addAttribute("username", person.getName());
			model.addAttribute("roles", authentication.getAuthorities().toString());
			return "dashboard.html";
		} else {
			return "regirect:/login?error=true";
		}
	}
}
