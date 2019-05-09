package com.rps.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rps.dao.DegreeDao;
import com.rps.dao.FinalExamDao;
import com.rps.dao.MarkDistributionDao;
import com.rps.dao.StudentDao;
import com.rps.entities.Degree;
import com.rps.entities.FinalExam;
import com.rps.entities.MarkDistribution;
import com.rps.entities.Student;


@Controller
@RequestMapping("/")
public class HomeController {
	
	
	@Autowired
	StudentDao studentDao;
	@Autowired
	DegreeDao degreeDao;
	@Autowired
	FinalExamDao finalExamDao;
	@Autowired
	MarkDistributionDao markDistributionDao;
	
	@RequestMapping("")
	public String welcome()
	{
		return "welcome";
//		return "redirect:/studentslist";
	}
	
	@RequestMapping(value="/updateStudent", method=RequestMethod.GET)
	public String updateStudent(Model model)
	{
		Student student = new Student();
		model.addAttribute("student", student);
		return "update-student-form";
	}
	
	@RequestMapping(value = "/updateStudent", method=RequestMethod.POST)
	public String updateStudentPost(@ModelAttribute("student") Student student)
	{
		studentDao.save(student);
		return "welcome";
	}
	
	@RequestMapping(value="/showStudent", method=RequestMethod.GET)
	public String showStudentGET() 
	{
		return "find-student-form";
	}
	
	@RequestMapping(value="/showStudent", method=RequestMethod.POST)
	public String showStudent(@RequestParam String dept, @RequestParam String session,  Model model) 
	{
		List<Student> studentlist = studentDao.findByDeptAndSession(dept, session);
		
		Collections.sort(studentlist);
		
		model.addAttribute("studentlist", studentlist);
		
		return "student-list";
	}
	
	@RequestMapping(value="/showDetailDegrees/{regNumber}", method=RequestMethod.GET)
	public String showDegrees(@PathVariable("regNumber") String regNumber, Model model)
	{
		Student student = studentDao.findByRegNumber(regNumber);
		
		List<Degree> degreelist = degreeDao.findByStudent(student);
		
		model.addAttribute("student", student);
		model.addAttribute("degreelist", degreelist);
		return "show-student-detail-degrees";
	}
	
	@RequestMapping(value="/showDetailDegrees/{regNumber}", method=RequestMethod.POST)
	public String showDegreesPost(@PathVariable("regNumber") String regNumber, @RequestParam String degreeName, Model model)
	{
		Student student = studentDao.findByRegNumber(regNumber);
		
		List<Degree> degreelist = degreeDao.findByStudentAndDegreeName(student, degreeName);
		
		if(degreelist.isEmpty()) degreeDao.save(new Degree(degreeName, 0.0, student));
		else System.out.println("Degree Already Present!");
		
		degreelist = degreeDao.findByStudent(student);
		
		model.addAttribute("student", student);
		model.addAttribute("degreelist", degreelist);
		return "show-student-detail-degrees";
	}
	
	
	@RequestMapping(value="/showDetailDegrees/{regNumber}/{degreeId}", method=RequestMethod.GET)
	public String showFinalExams(@PathVariable String regNumber, @PathVariable Long degreeId, Model model)
	{
		Degree degree = degreeDao.findByDegreeId(degreeId);
		List<FinalExam> finalexamlist = finalExamDao.findByDegree(degree);
		Student student = studentDao.findByRegNumber(regNumber);
		
		model.addAttribute("finalexamlist", finalexamlist);
		model.addAttribute("degree", degree);
		model.addAttribute("student", student);
		return "final-exam-list";
	}
	
	@RequestMapping(value="/showDetailDegrees/{regNumber}/{degreeId}", method=RequestMethod.POST)
	public String showFinalExamsPost(@PathVariable String regNumber, @PathVariable Long degreeId, @RequestParam String examName, Model model)
	{
		Degree degree = degreeDao.findByDegreeId(degreeId);
		FinalExam finalexam = new FinalExam(examName, new BigDecimal(0.0), degree);
		finalExamDao.save(finalexam);
		
		List<FinalExam> finalexamlist = finalExamDao.findByDegree(degree);
		Student student = studentDao.findByRegNumber(regNumber);
		
		model.addAttribute("finalexamlist", finalexamlist);
		model.addAttribute("degree", degree);
		model.addAttribute("student", student);
		
		return "final-exam-list";
	}
	
	@RequestMapping(value="/showDetailDegrees/{regNumber}/{degreeId}/{finalExamId}", method=RequestMethod.GET)
	public String marksheetAddShow(@PathVariable String regNumber, @PathVariable Long degreeId, @PathVariable Long finalExamId, Model model)
	{
		Degree degree = degreeDao.findByDegreeId(degreeId);
		
		FinalExam finalExam = finalExamDao.findByExamId(finalExamId);
		Student student = studentDao.findByRegNumber(regNumber);
		
		List<MarkDistribution> marklist = markDistributionDao.findByFinalExam(finalExam);
		
		
		BigDecimal gpa = new BigDecimal(0);
		BigDecimal tmp = new BigDecimal(0);
		BigDecimal totalCredit = new BigDecimal(0);
		
		for(MarkDistribution mark: marklist)
		{
			tmp = mark.getGradePoint().multiply(mark.getNoOfCredit());
			gpa = gpa.add( tmp ) ;
			totalCredit = totalCredit.add(mark.getNoOfCredit());
		}
		if(totalCredit.compareTo(new BigDecimal(0))!=0)
			gpa = gpa.divide(totalCredit, 2, RoundingMode.HALF_UP);
		
		finalExam.setGPA(gpa);
		finalExamDao.save(finalExam);
		
		BigDecimal tmpCgpa = BigDecimal.ZERO;
		
		List<FinalExam> finalexamlist = finalExamDao.findByDegree(degree);
		tmp = BigDecimal.ZERO;
		
		for(FinalExam finalexam: finalexamlist)
		{
			//if(finalexam.getGPA().compareTo(new BigDecimal(0.0))!=0) cnt++;
			tmpCgpa = tmpCgpa.add(finalexam.getGPA());
		}
		
		if(finalexamlist.size()>0)
			tmpCgpa = tmpCgpa.divide(new BigDecimal(finalexamlist.size()), 2, RoundingMode.HALF_UP);
		
		degree.setCumulativeGPAtillNow(Double.parseDouble(tmpCgpa.toString()));
		degreeDao.save(degree);
		
		
		model.addAttribute("markObj", new MarkDistribution());
		model.addAttribute("marklist", marklist);
		model.addAttribute("exam", finalExam);
		model.addAttribute("degree", degree);
		model.addAttribute("student", student);
		
		return "marksheet-show-and-update";
	}
	
	@RequestMapping(value="/showDetailDegrees/{regNumber}/{degreeId}/{finalExamId}", method=RequestMethod.POST)
	public String marksheetAddShowPost(@ModelAttribute("markObj") MarkDistribution markObj, @PathVariable String regNumber, @PathVariable Long degreeId, @PathVariable Long finalExamId, Model model)
	{
	
		FinalExam finalExam = finalExamDao.findByExamId(finalExamId);
		markObj.setFinalExam(finalExam);
		
		markDistributionDao.save(markObj);
		
		Degree degree = degreeDao.findByDegreeId(degreeId);
		
		Student student = studentDao.findByRegNumber(regNumber);
		
		List<MarkDistribution> marklist = markDistributionDao.findByFinalExam(finalExam);
		
		
		
		model.addAttribute("marklist", marklist);
		model.addAttribute("exam", finalExam);
		model.addAttribute("degree", degree);
		model.addAttribute("student", student);
		
		return "marksheet-show-and-update";
	}
	
	
	
}
