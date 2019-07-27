package com.rps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rps.entities.storage.Student;
import com.rps.entities.tabulation.Teacher;
import com.rps.service.storage.StudentService;

@Controller
@RequestMapping("/storage")
public class StorageController {
	
	@Autowired
	StudentService studentService;
	
	@GetMapping(value="")
	public String welcomePage()
	{
		return "storage/a-welcome-page";
	}

	@GetMapping(value="/addStudent")
	public String tsignup(Model model)
	{
		Student student = new Student();
		model.addAttribute("student", student);
		return "storage/b-stuent-add";
	}
	
	@PostMapping(value="/addStudent")
	public String tsignupPost(@ModelAttribute("student") Student student)
	{
		studentService.addStudent(student);
		return "storage/a-welcome-page";
	}
	
}
