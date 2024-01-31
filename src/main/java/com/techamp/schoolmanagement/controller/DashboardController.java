package com.techamp.schoolmanagement.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class DashboardController {
	@GetMapping(value = "/dashboard")
	public String getDashboard(Model model, Authentication authentication) {
		
		model.addAttribute("username", authentication.getName());
		model.addAttribute("roles", authentication.getAuthorities().toString());
//		throw new RuntimeException("just testing exception handling");
		return "dashboard.html";
	}
}
