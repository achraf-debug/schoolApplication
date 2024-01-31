package com.techamp.schoolmanagement.controller;


import com.techamp.schoolmanagement.model.Holiday;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HolidaysController {
	
	@GetMapping(value = "/holidays")
	public String getHolidays(Model model,
														@RequestParam(required = false) Boolean festival,
														@RequestParam(required = false) Boolean federal) {
		List<Holiday> holidays = List.of(
		new Holiday("jan 26", "Achraf's Birthday", Holiday.Type.FESTIVAL),
		new Holiday("March 20", "Independancy Day", Holiday.Type.FEDERAL),
		new Holiday("April 15", "Eid AlFiter", Holiday.Type.FEDERAL),
		new Holiday("May 27", "Eid Idhha", Holiday.Type.FEDERAL),
		new Holiday("June 26", "Mother's Day", Holiday.Type.FESTIVAL),
		new Holiday("July 01", "Labor Day", Holiday.Type.FEDERAL),
		new Holiday("jan 26", "Achraf's Birthday", Holiday.Type.FESTIVAL),
		new Holiday("jan 26", "Achraf's Birthday", Holiday.Type.FEDERAL),
		new Holiday("jan 26", "Achraf's Birthday", Holiday.Type.FESTIVAL)
		);
		
		Holiday.Type[] types = Holiday.Type.values();
		for (Holiday.Type type : types) {
			model.addAttribute(type.toString(),
			holidays.stream().filter(it -> it.getType().equals(type)).toList());
		}
		
		
		return "holidays.html";
	}
	
}
