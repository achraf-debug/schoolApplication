package com.techamp.schoolmanagement.controller;

import com.techamp.schoolmanagement.model.Address;
import com.techamp.schoolmanagement.model.Person;
import com.techamp.schoolmanagement.model.Profile;
import com.techamp.schoolmanagement.repositories.PersonRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileController {
	
	@Autowired
	private PersonRepository personRepository;
	
	@GetMapping("/displayProfile")
	public ModelAndView getProfile(HttpSession httpSession) {
		Person person = (Person) httpSession.getAttribute("loggedPerson");
		Profile profile = new Profile();
		profile.setName(person.getName());
		profile.setMobileNumber(person.getMobileNumber());
		profile.setEmail(person.getEmail());
		if (person.getAddress() !=null && person.getAddress().getAddressId()>0){
			profile.setAddress1(person.getAddress().getAddress1());
			profile.setAddress2(person.getAddress().getAddress2());
			profile.setCity(person.getAddress().getCity());
			profile.setState(person.getAddress().getState());
			profile.setZipCode(person.getAddress().getZipCode());
		}
		ModelAndView modelAndView = new ModelAndView("profile.html");
		modelAndView.addObject("profile",profile);
		return modelAndView;
	}
	
	@PostMapping("/updateProfile")
	public String updateProfile(@Valid @ModelAttribute("profile") Profile profile, Errors errors,
															HttpSession session)
	{
		if(errors.hasErrors()){
			return "profile.html";
		}
		Person person = (Person) session.getAttribute("loggedPerson");
		person.setName(profile.getName());
		person.setEmail(profile.getEmail());
		person.setMobileNumber(profile.getMobileNumber());
		if(person.getAddress() == null || !(person.getAddress().getAddressId()>0)){
			person.setAddress(new Address());
		}
		person.getAddress().setAddress1(profile.getAddress1());
		person.getAddress().setAddress2(profile.getAddress2());
		person.getAddress().setCity(profile.getCity());
		person.getAddress().setState(profile.getState());
		person.getAddress().setZipCode(profile.getZipCode());
		Person savedPerson = personRepository.save(person);
		session.setAttribute("loggedPerson", savedPerson);
		return "redirect:/displayProfile";
	}
}
