package com.rps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RootController {
	
	@RequestMapping("/")
	public String welcome()
	{
		return "redirect:/tabulation/showExams";
	}

}
