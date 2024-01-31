package com.techamp.schoolmanagement.controller;

import com.techamp.schoolmanagement.constants.Constants;
import com.techamp.schoolmanagement.model.ContactMessage;
import com.techamp.schoolmanagement.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ContactController {
	
	@Autowired
	private final ContactService contactService;
	
	@PostMapping(value = "/saveMsg")
	public String saveMessage(@Valid @ModelAttribute("contactMessage") ContactMessage contactMessage, Errors errors, RedirectAttributes redirectAttributes) {
		System.out.println(contactMessage.toString());
		if(errors.hasErrors()) {
			log.error("Contact Message form validation failed due to: " + errors);
			return "contact.html";
		}
		
		contactService.saveMessageDetails(contactMessage);
		redirectAttributes.addFlashAttribute("message", "Message sent successfully !");
		return "redirect:/contact";
	}
	
	@GetMapping(value = "/contact")
	public String getContact(Model model) {
		model.addAttribute("contactMessage", new ContactMessage());
		return "contact.html";
	}
	
	@GetMapping(value = "/displayMessages")
	public ModelAndView displayOpenMessages() {
		List<ContactMessage> contactMessages = contactService.getMessagesWithOpenStatus();
		ModelAndView modelAndView = new ModelAndView("messages.html");
		modelAndView.addObject("contactMessages", contactMessages);
		
		return modelAndView;
	}
	
	@GetMapping(value = "/closeMsg")
	public String closeMessage(@RequestParam int id, Authentication authentication, Model model) {
		contactService.updateMessageStatus(id, Constants.CLOSE, authentication.getName());
		
		return "redirect:/displayMessages";
	}
}


