//package com.rps.controller;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.util.Collections;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Set;
//
//import javax.transaction.Transactional;
//
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.rps.dao.storage.CourseDao;
//import com.rps.dao.storage.FinalExamDao;
//import com.rps.dao.storage.ProgramDao;
//import com.rps.dao.storage.StudentDao;
//import com.rps.entities.storage.Course;
//import com.rps.entities.storage.FinalExam;
//import com.rps.entities.storage.Program;
//import com.rps.entities.storage.Student;
//
//
////@Controller
//@RequestMapping("/")
//public class HomeController {
//	
//	@Autowired
//	StudentDao studentDao;
//	@Autowired
//	Program programDao;
//	@Autowired
//	FinalExam finalExamDao;
//	@Autowired
//	Course courseDao;
//	
//	@RequestMapping("")
//	public String welcome()
//	{
//		return "welcome";
////		return "redirect:/studentslist";
//	}
//	
//	@RequestMapping(value="/updateStudent", method=RequestMethod.GET)
//	public String updateStudent(Model model)
//	{
//		Student student = new Student();
//		model.addAttribute("student", student);
//		return "update-student-form";
//	}
//	
//	@RequestMapping(value = "/updateStudent", method=RequestMethod.POST)
//	public String updateStudentPost(@ModelAttribute("student") Student student, Model model)
//	{
//		if(studentDao.findByExamRollOrRegNumber(student.getExamRoll(), student.getRegNumber()).isEmpty()) {			
//			studentDao.save(student);
//			model.addAttribute("student", student);
//			model.addAttribute("registrationOkay", true);
//			return "welcome";
//		}else 
//		{
//			model.addAttribute("studentExists", true);
//			model.addAttribute("student", student);
//			return "update-student-form";
//		}
//		
//	}
//	
//	@RequestMapping(value="/showStudent", method=RequestMethod.GET)
//	public String showStudentGET() 
//	{
//		return "find-student-form";
//	}
//	
//	@RequestMapping(value="/showStudent", method=RequestMethod.POST)
//	public String showStudent(@RequestParam String dept, @RequestParam String session,  Model model) 
//	{
//		List<Student> studentlist = studentDao.findByDeptAndSession(dept, session);
//		
//		Collections.sort(studentlist);
//		
//		model.addAttribute("studentlist", studentlist);
//		
//		return "student-list";
//	}
//	
//	@RequestMapping(value="/showDetailDegrees/{regNumber}", method=RequestMethod.GET)
//	public String showDegrees(@PathVariable("regNumber") String regNumber, Model model)
//	{
//		Student student = studentDao.findByRegNumber(regNumber);
//		
//		List<Program> degreelist = programDao.findByStudent(student);
//		
//		model.addAttribute("student", student);
//		model.addAttribute("degreelist", degreelist);
//		return "show-student-detail-degrees";
//	}
	
//	
//	@RequestMapping(value="/showDetailDegrees/{regNumber}", method=RequestMethod.POST)
//	public String showDegreesPost(@PathVariable("regNumber") String regNumber, @RequestParam String programCode, Model model, @RequestParam("file") MultipartFile excelfile) throws IOException
//	{
//		
//		Student student = studentDao.findByRegNumber(regNumber);
//		Program programObj = new Program(programCode, 0.0, student);
//		
//		List<Program> sameprogramCodeprogramList = programDao.findByStudentAndProgramCode(student, programCode);
//		
//		if(sameprogramCodeprogramList.isEmpty()) 
//		{
//			programDao.save(programObj);
//			
//			//process course syllabus excel sheet
//			XSSFWorkbook workbook = new XSSFWorkbook(excelfile.getInputStream());
//			long totalSheets = workbook.getNumberOfSheets();
//			
//			
//			for(int i=0; i<totalSheets; i++)
//			{
//				XSSFSheet worksheet = workbook.getSheetAt(i);
//			    //exam name set
//			    String examName = worksheet.getSheetName();
////			    System.out.println(examName);
////			    System.out.println("hiiiiiiiiiiiiii");
//			    long examNumber = (long) worksheet.getRow(0).getCell(3).getNumericCellValue();
////			    System.out.println(examNumber + " ---------- ");
//			    FinalExam finalExam = new FinalExam(examNumber, examName, new BigDecimal(0.0), programObj);
//				finalExamDao.save(finalExam);
//				boolean flag = false;
//				
//			    for(int j=1;j<worksheet.getPhysicalNumberOfRows();j++) {
//
//			        XSSFRow row = worksheet.getRow(j);
//			        
//					/*
//					 * if(row.getCell(1).getStringCellValue()==null) {
//					 * model.addAttribute("sheetName", row.getSheet().toString()); break; }
//					 */
//			        if(row.getCell(1).getStringCellValue().equalsIgnoreCase("EndCell")==true){
//						 flag = true;
//						 break;
//					 }
//			        
//			        long code = (int) row.getCell(0).getNumericCellValue();
//			        String courseCode = String.valueOf(code);
//			        String courseName = row.getCell(1).getStringCellValue();
//			        
//			        System.out.println(courseCode + " "  +courseName+"-----" + worksheet.getPhysicalNumberOfRows());
//			        BigDecimal noOfCredit = new BigDecimal(row.getCell(2).getNumericCellValue());
//			        
//			        courseDao.save(new Course(courseCode, courseName, noOfCredit, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, finalExam));
//			    }
//			    
//			    if(flag==false)
//			    {
//			    	model.addAttribute("excelFormatErrorExists", true);
//			    	break;
//			    }
//			    
//			}
//			
//			
//		}
//		else 
//		{
//			model.addAttribute("programExists", true);
//		}
//	
//		
//		
//		sameprogramCodeprogramList = programDao.findByStudent(student);
//		
//		model.addAttribute("student", student);
//		model.addAttribute("degreelist", sameprogramCodeprogramList);
//		return "show-student-detail-degrees";
//	}
	
	
//	@RequestMapping(value="/showDetailDegrees/{regNumber}/{programId}", method=RequestMethod.GET)
//	public String showFinalExams(@PathVariable String regNumber, @PathVariable Long programId, Model model)
//	{
//		Program program = programDao.findByProgramId(programId);
//		
//		List<FinalExam> finalexamlist = finalExamDao.findByProgram(program);
//		Student student = studentDao.findByRegNumber(regNumber);
//		
//		model.addAttribute("finalexamlist", finalexamlist);
//		model.addAttribute("program", program);
//		model.addAttribute("student", student);
//		return "final-exam-list";
//	}
//	
//	@Transactional
//	@RequestMapping(value="/showDetailDegrees/{regNumber}/{programId}/del", method=RequestMethod.GET)
//	public String deleteProgram(@PathVariable String regNumber, @PathVariable Long programId, Model model)
//	{
//		programDao.deleteById(programId);
//		
//		Student student = studentDao.findByRegNumber(regNumber);
//		
//		List<Program> degreelist = programDao.findByStudent(student);
//		
//		model.addAttribute("student", student);
//		model.addAttribute("degreelist", degreelist);
//		return "show-student-detail-degrees";
//	}
//
//	@RequestMapping(value="/showDetailDegrees/{regNumber}/{programId}", method=RequestMethod.POST)
//	public String showFinalExamsPost(@PathVariable String regNumber, @PathVariable Long programId, @RequestParam String examName, Model model)
//	{
//		Program program = programDao.findByProgramId(programId);
//		FinalExam finalExam = new FinalExam((long)111111, examName, new BigDecimal(0.0), program);
//		finalExamDao.save(finalExam);
//		
//		List<FinalExam> finalexamlist = finalExamDao.findByProgram(program);
//		Student student = studentDao.findByRegNumber(regNumber);
//		
//		model.addAttribute("finalexamlist", finalexamlist);
//		model.addAttribute("degree", program);
//		model.addAttribute("student", student);
//		
//		return "final-exam-list";
//	}
//	
//	@RequestMapping(value="/showDetailDegrees/{regNumber}/{programId}/{finalExamId}", method=RequestMethod.GET)
//	public String marksheetAddShow(@PathVariable String regNumber, @PathVariable Long programId, @PathVariable Long finalExamId, Model model)
//	{
//		
//		Student student = studentDao.findByRegNumber(regNumber);
//		Program program = programDao.findByProgramId(programId);
//		FinalExam finalExam = finalExamDao.findByExamId(finalExamId);
//		List<Course> marklist = courseDao.findByFinalExam(finalExam);
//
//		/*
//		BigDecimal gpa = BigDecimal.ZERO;//new BigDecimal("0");
//		BigDecimal tmp = BigDecimal.ZERO;//new BigDecimal("0");
//		BigDecimal totalCredit = BigDecimal.ZERO;// new BigDecimal("0");
//		
//		for(Course mark: marklist)
//		{
//			tmp = mark.getGradePoint().multiply(mark.getNoOfCredit());
//			gpa = gpa.add( tmp ) ;
//			totalCredit = totalCredit.add(mark.getNoOfCredit());
//		}
//		if(totalCredit.compareTo(new BigDecimal(0))!=0)
//			gpa = gpa.divide(totalCredit, 2, RoundingMode.HALF_UP);
//		
//		/////////////////////////////////////
//		finalExam.setGPA(gpa);
//		finalExamDao.save(finalExam);
//		
//		
//		BigDecimal tmpCgpa = BigDecimal.ZERO;
//		
//		List<FinalExam> finalexamlist = finalExamDao.findByProgram(program);
//		tmp = BigDecimal.ZERO;
//		int occuredExamCnt = 0;
//		
//		for(FinalExam finalexam: finalexamlist)
//		{
//			if(finalexam.getGPA().compareTo(tmp)!=0) occuredExamCnt++;
//			tmpCgpa = tmpCgpa.add(finalexam.getGPA());
//		}
//		
//		occuredExamCnt = Math.max(1, occuredExamCnt);
//		
//		if(finalexamlist.size()>0)
//			tmpCgpa = tmpCgpa.divide(new BigDecimal(occuredExamCnt), 2, RoundingMode.HALF_UP);
//		
//		////////////////////////
//		program.setCumulativeGPAtillNow(Double.parseDouble(tmpCgpa.toString()));
//		programDao.save(program);
//   */		
//		model.addAttribute("marklist", marklist);
//		model.addAttribute("exam", finalExam);
//		model.addAttribute("program", program);
//		model.addAttribute("student", student);
//		
//		return "marksheet-show-and-update";
//	}
//	
//	@RequestMapping(value="/showDetailDegrees/{regNumber}/{programId}/{finalExamId}/{courseId}", method=RequestMethod.GET)
//	public String marksheetAddShow(@PathVariable String regNumber, @PathVariable Long programId, @PathVariable Long finalExamId, @PathVariable Long courseId, Model model)
//	{
//		
//		Course markObj = courseDao.findByCourseId(courseId);
//		
//		FinalExam finalExam = finalExamDao.findByExamId(finalExamId);
//		List<Course> marklist = courseDao.findByFinalExam(finalExam);
//		
//		Program program = programDao.findByProgramId(programId);
//		
//		Student student = studentDao.findByRegNumber(regNumber);
//	
//		//System.out.println(markObj.getFinalExam().getExamId());
//		
//		model.addAttribute("markObj", markObj);
//		model.addAttribute("exam", finalExam);
//		model.addAttribute("marklist", marklist);
//		model.addAttribute("degree", program);
//		model.addAttribute("student", student);
//		model.addAttribute("program", program);
//		
//		return "mark-single-update";
//	}
//	
//	
//	@RequestMapping(value="/showDetailDegrees/{regNumber}/{programId}/{finalExamId}/{courseId}", method=RequestMethod.POST)
//	public String marksheetAddShowAfterMarkUpdate(@ModelAttribute Course markObj, @PathVariable Long courseId, @PathVariable String regNumber, @PathVariable Long programId, @PathVariable Long finalExamId, Model model)
//	{
//		Student student = studentDao.findByRegNumber(regNumber);
//		Program program = programDao.findByProgramId(programId);
//		FinalExam finalExam = finalExamDao.findByExamId(finalExamId);
//		List<Course> marklist = courseDao.findByFinalExam(finalExam);
//		
//		markObj.setMarkAchievedby100(markObj.getTutorialMark().add(markObj.getFinalExamMark()));
//		BigDecimal totalMark = markObj.getMarkAchievedby100();
//		double gradePoint = 0;
//		
//		if(totalMark.compareTo(new BigDecimal(80))>=0) gradePoint = 4.0;
//		else if(totalMark.compareTo(new BigDecimal(75))>=0) gradePoint = 3.75;
//		else if(totalMark.compareTo(new BigDecimal(70))>=0) gradePoint = 3.5;
//		else if(totalMark.compareTo(new BigDecimal(65))>=0) gradePoint = 3.25;
//		else if(totalMark.compareTo(new BigDecimal(60))>=0) gradePoint = 3.0; 
//		else if(totalMark.compareTo(new BigDecimal(55))>=0) gradePoint = 2.75;
//		else if(totalMark.compareTo(new BigDecimal(50))>=0) gradePoint = 2.5;
//		else if(totalMark.compareTo(new BigDecimal(45))>=0) gradePoint = 2.25;
//		else if(totalMark.compareTo(new BigDecimal(40))>=0) gradePoint = 2.0;
//		else gradePoint = 0.0;
//		
//		markObj.setGradePoint(new BigDecimal(gradePoint));
//		markObj.setFinalExam(finalExam);
//		markObj.setCourseId(courseId);
//		//System.out.println(markObj.getFinalExam().getExamId());
//		courseDao.save(markObj);
//		
//		
//		
//		/////////////////////////////////////// update gpa,cgpa
//		
//		
//		BigDecimal gpa = BigDecimal.ZERO;//new BigDecimal("0");
//		BigDecimal tmp = BigDecimal.ZERO;//new BigDecimal("0");
//		BigDecimal totalCredit = BigDecimal.ZERO;// new BigDecimal("0");
//		
//		for(Course mark: marklist)
//		{
//			tmp = mark.getGradePoint().multiply(mark.getNoOfCredit());
//			gpa = gpa.add( tmp ) ;
//			totalCredit = totalCredit.add(mark.getNoOfCredit());
//		}
//		if(totalCredit.compareTo(new BigDecimal(0))!=0)
//			gpa = gpa.divide(totalCredit, 2, RoundingMode.HALF_UP);
//		
//		////////
//		finalExam.setGPA(gpa);
//		finalExamDao.save(finalExam);
//		
//		
//		BigDecimal tmpCgpa = BigDecimal.ZERO;
//		
//		List<FinalExam> finalexamlist = finalExamDao.findByProgram(program);
//		tmp = BigDecimal.ZERO;
//		int occuredExamCnt = 0;
//		
//		for(FinalExam finalexam: finalexamlist)
//		{
//			if(finalexam.getGPA().compareTo(tmp)!=0) occuredExamCnt++;
//			tmpCgpa = tmpCgpa.add(finalexam.getGPA());
//		}
//		
//		occuredExamCnt = Math.max(1, occuredExamCnt);
//		
//		if(finalexamlist.size()>0)
//			tmpCgpa = tmpCgpa.divide(new BigDecimal(occuredExamCnt), 2, RoundingMode.HALF_UP);
//		
//		//////
//		program.setCumulativeGPAtillNow(Double.parseDouble(tmpCgpa.toString()));
//		programDao.save(program);
//		
//		///////////////////////////////////////
//		
//		model.addAttribute("marklist", marklist);
//		model.addAttribute("exam", finalExam);
//		model.addAttribute("program", program);
//		model.addAttribute("student", student);
//		
//		return "marksheet-show-and-update";
//	}
//	
//	@GetMapping("/batchMarkUpload")
//	public String batchMarkUpload()
//	{
//		return "teacher-bulk-mark-update";
//	}
//	
//	@PostMapping("/batchMarkUpload")
//	public String batchMarkUploadPost(@RequestParam("file") MultipartFile teacherExcel, Model model) throws IOException
//	{
//		/* process and parse data from the excel file */
//		
//		//find the student
//		XSSFWorkbook workbook = new XSSFWorkbook(teacherExcel.getInputStream());
//		XSSFSheet worksheet = workbook.getSheetAt(0);
//
//		String programCode = worksheet.getRow(1).getCell(1).toString();
//		long examNumber = (long) worksheet.getRow(1).getCell(2).getNumericCellValue();
//		String courseCode = String.valueOf((long) worksheet.getRow(1).getCell(4).getNumericCellValue());
//		
//	//	System.out.println(courseCode + "  --------  ");
//		boolean flag = false;
//		
//		 for(int j=4;j<worksheet.getPhysicalNumberOfRows();j++) {
//			 
//			 XSSFRow row = worksheet.getRow(j);
//			 
//			 if(row.getCell(1).getStringCellValue().equalsIgnoreCase("EndCell")==true){
//				 flag = true;
//				 break;
//			 }
//		        
//		     String session = row.getCell(1).toString();
//		     //to remove point from converted string did this overhead double conversion work
//		     String regNumber = String.valueOf( (long) row.getCell(2).getNumericCellValue() );
//		     String examRoll = String.valueOf( (long) row.getCell(3).getNumericCellValue() );
//		     
//		     BigDecimal tutorialMark = new BigDecimal(row.getCell(4).getNumericCellValue());
//		     BigDecimal finalExamMark = new BigDecimal(row.getCell(5).getNumericCellValue());
//		     BigDecimal totalMark = new BigDecimal(row.getCell(6).getNumericCellValue());
//		     BigDecimal gradePoint = new BigDecimal(row.getCell(7).getNumericCellValue());
//		     
//		     System.out.println(examRoll+ " " + finalExamMark + " " + totalMark + " " + gradePoint);
//		     
//		     Student student = studentDao.findByRegNumber(regNumber);
//		     
//		     if(student==null) 
//		     {
//		    	 model.addAttribute("regNumber", regNumber);
//		    	 flag = false;
//		    	 break;
//		     }
//		     
//		     Set<Program> programset = student.getPrograms();
//		     
//		     Program program = null;
//		     
//		     for(Iterator<Program> iter = programset.iterator(); iter.hasNext(); )
//		     {
//		    	 program = iter.next();
//		    	 if(program.getProgramCode().equals(programCode))
//		    	 {
//		    		 break;
//		    	 }
//		     }
//		     
//		    // System.out.println(program.getProgramCode() + " " + programCode);
//		     
//		     Set<FinalExam> finalExamSet = program.getFinalExams();
//		    // System.out.println(finalExamset.size());
//		     FinalExam finalExam = null;
//		     
//		     for(Iterator<FinalExam> iter = finalExamSet.iterator(); iter.hasNext(); )
//		     {
//		    	 finalExam = iter.next();
//		    	 if(finalExam.getExamNumber().equals(examNumber))
//		    	 {
//		    		 break;
//		    	 }
//		     }
//		     
//		     Set<Course> courseSet = finalExam.getCourses();
//		     Course course = null;
//		     for(Iterator<Course> iter = courseSet.iterator(); iter.hasNext(); )
//		     {
//		    	 course = iter.next();
//		    	 if(course.getCourseCode().equals(courseCode))
//		    	 {
//		    		 break;
//		    	 }
//		     }
//		     
//		     System.out.println(course.getCourseId() + "  --------  ");
//		     System.out.println(course.getCourseCode());
//		     System.out.println(course.getCourseName());
//		     
//		     //finally got the desired course
//		     course.setTutorialMark(tutorialMark);
//		     course.setFinalExamMark(finalExamMark);
//		     course.setMarkAchievedby100(totalMark);
//		     course.setGradePoint(gradePoint);
//		     courseDao.save(course);
//		     
//		     
//				/////////////////////////////////////// update gpa,cgpa
//				
//		     	List<Course> courselist = courseDao.findByFinalExam(finalExam);
//				
//				BigDecimal gpa = BigDecimal.ZERO;//new BigDecimal("0");
//				BigDecimal tmp = BigDecimal.ZERO;//new BigDecimal("0");
//				BigDecimal totalCredit = BigDecimal.ZERO;// new BigDecimal("0");
//				
//				for(Course mark: courselist)
//				{
//					tmp = mark.getGradePoint().multiply(mark.getNoOfCredit());
//					gpa = gpa.add( tmp ) ;
//					totalCredit = totalCredit.add(mark.getNoOfCredit());
//				}
//				if(totalCredit.compareTo(new BigDecimal(0))!=0)
//					gpa = gpa.divide(totalCredit, 2, RoundingMode.HALF_UP);
//				
//				////////
//				finalExam.setGPA(gpa);
//				finalExamDao.save(finalExam);
//				
//				
//				BigDecimal tmpCgpa = BigDecimal.ZERO;
//				
//				List<FinalExam> finalexamlist = finalExamDao.findByProgram(program);
//				tmp = BigDecimal.ZERO;
//				int occuredExamCnt = 0;
//				
//				for(FinalExam finalexam: finalexamlist)
//				{
//					if(finalexam.getGPA().compareTo(tmp)!=0) occuredExamCnt++;
//					tmpCgpa = tmpCgpa.add(finalexam.getGPA());
//				}
//				
//				occuredExamCnt = Math.max(1, occuredExamCnt);
//				
//				if(finalexamlist.size()>0)
//					tmpCgpa = tmpCgpa.divide(new BigDecimal(occuredExamCnt), 2, RoundingMode.HALF_UP);
//				
//				//////
//				program.setCumulativeGPAtillNow(Double.parseDouble(tmpCgpa.toString()));
//				programDao.save(program);
//		     
//		 }
//		 
//		 if(flag==true)
//		 {
//			model.addAttribute("batchmarkUpdateOkay", true);
//		 }else
//		 {
//			 model.addAttribute("batchmarkUpdateFailed", true);
//			 
//		 }
//		 return "welcome";
//	}
//}
