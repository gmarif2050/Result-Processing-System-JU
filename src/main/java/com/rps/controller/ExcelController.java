package com.rps.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.rps.entities.storage.Course;
import com.rps.entities.storage.FinalExam;
import com.rps.entities.tabulation.TCourse;
import com.rps.entities.tabulation.TExam;
import com.rps.entities.tabulation.TMark;
import com.rps.entities.tabulation.TStudent;
import com.rps.entities.tabulation.Teacher;
import com.rps.service.storage.StudentService;
import com.rps.service.tabulation.TCourseService;
import com.rps.service.tabulation.TExamService;
import com.rps.service.tabulation.TMarkService;
import com.rps.service.tabulation.TStudentService;
import com.rps.service.tabulation.TeacherService;
import com.rps.utility.tabulation.TStudentListContainer;

@Controller
public class ExcelController extends HttpServlet{
	
	
	@Autowired
	TeacherService teacherService;
	@Autowired
	TExamService texamService;
	@Autowired
	TCourseService tcourseService;
	@Autowired
	TStudentService tstudentService;
	@Autowired
	StudentService studentService;
	@Autowired
	TMarkService tmarkService;

	@GetMapping(value="/tabulation/{teacherId}/exam/{texamId}/course/{tcourseId}/tutorialMarkEntryExcelDownload")
	public void tutorialExcelGen(HttpServletResponse response,Model model, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId, @PathVariable("tcourseId") long tcourseId) throws IOException, ServletException
	{
		model.addAttribute("teacherId", teacherId);
		Teacher teacher = teacherService.getTeacher(teacherId);
		
		TExam texam = texamService.getExam(texamId);
		model.addAttribute("texam", texam);
		
		TCourse tcourse = tcourseService.getTCourse(tcourseId);
		model.addAttribute("tcourse", tcourse);
		
		Set<TStudent> tstudentSet =  tcourse.getTstudents();
		List<TStudent> tstudentList = new ArrayList<>(tstudentSet);
		Collections.sort(tstudentList);
		
		TStudentListContainer tstudentListContainer = new TStudentListContainer();
		tstudentListContainer.setTstudentList(tstudentList);
		
		model.addAttribute("tstudentListContainer", tstudentListContainer);	
	
		 try {
	         response.setContentType("application/vnd.ms-excel");
	         
	         String fileName = "attachment; filename=tutorialMark_" + tcourse.getTcourseCode() +"_" + texam.getTexamName() + "(" + texam.getSession() + ".xlsx";
	         response.setHeader("Content-Disposition", fileName);
	         XSSFWorkbook workbook = writeExcel(tstudentList,tcourse,texam);
	         workbook.write(response.getOutputStream());
	     } catch (Exception e) {
	         throw new ServletException("Exception in DownLoad Excel Servlet", e);
	     }
	}
	
	@GetMapping(value="/tabulation/{teacherId}/exam/{texamId}/course/{tcourseId}/internalMarkEntryExcelDownload")
	public void DoGet(HttpServletResponse response,Model model, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId, @PathVariable("tcourseId") long tcourseId) throws IOException, ServletException
	{
		model.addAttribute("teacherId", teacherId);
		Teacher teacher = teacherService.getTeacher(teacherId);
		
		TExam texam = texamService.getExam(texamId);
		model.addAttribute("texam", texam);
		
		TCourse tcourse = tcourseService.getTCourse(tcourseId);
		model.addAttribute("tcourse", tcourse);
		
		Set<TStudent> tstudentSet =  tcourse.getTstudents();
		List<TStudent> tstudentList = new ArrayList<>(tstudentSet);
		Collections.sort(tstudentList);
		
		TStudentListContainer tstudentListContainer = new TStudentListContainer();
		tstudentListContainer.setTstudentList(tstudentList);
		
		model.addAttribute("tstudentListContainer", tstudentListContainer);	
	
		 try {
	         response.setContentType("application/vnd.ms-excel");
	         
	         String fileName = "attachment; filename=InternalMark_" + tcourse.getTcourseCode() +"_" + texam.getTexamName() + "(" + texam.getSession() + ".xlsx";
	         response.setHeader("Content-Disposition", fileName);
	         XSSFWorkbook workbook = writeExcel(tstudentList,tcourse,texam);
	         workbook.write(response.getOutputStream());
	     } catch (Exception e) {
	         throw new ServletException("Exception in DownLoad Excel Servlet", e);
	     }
	
		  
		  
	}
	
	
	@GetMapping(value="/tabulation/{teacherId}/exam/{texamId}/course/{tcourseId}/externalMarkEntryExcelDownload")
	public void externalGenExcel(HttpServletResponse response,Model model, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId, @PathVariable("tcourseId") long tcourseId) throws IOException, ServletException
	{
		model.addAttribute("teacherId", teacherId);
		Teacher teacher = teacherService.getTeacher(teacherId);
		
		TExam texam = texamService.getExam(texamId);
		model.addAttribute("texam", texam);
		
		TCourse tcourse = tcourseService.getTCourse(tcourseId);
		model.addAttribute("tcourse", tcourse);
		
		Set<TStudent> tstudentSet =  tcourse.getTstudents();
		List<TStudent> tstudentList = new ArrayList<>(tstudentSet);
		Collections.sort(tstudentList);
		
		TStudentListContainer tstudentListContainer = new TStudentListContainer();
		tstudentListContainer.setTstudentList(tstudentList);
		
		model.addAttribute("tstudentListContainer", tstudentListContainer);	
	
		 try {
	         response.setContentType("application/vnd.ms-excel");
	         
	         String fileName = "attachment; filename=ExternalMark_" + tcourse.getTcourseCode() +"_" + texam.getTexamName() + "(" + texam.getSession() + ".xlsx";
	         response.setHeader("Content-Disposition", fileName);
	         XSSFWorkbook workbook = writeExcel(tstudentList,tcourse,texam);
	         workbook.write(response.getOutputStream());
	     } catch (Exception e) {
	         throw new ServletException("Exception in DownLoad Excel Servlet", e);
	     }
	
		  
		  
	}
	
	@GetMapping(value="/tabulation/{teacherId}/exam/{texamId}/course/{tcourseId}/thirdExaminerMarkEntryExcelDownload")
	public void thirdExamGenExcel(HttpServletResponse response,Model model, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId, @PathVariable("tcourseId") long tcourseId) throws IOException, ServletException
	{
		model.addAttribute("teacherId", teacherId);
		Teacher teacher = teacherService.getTeacher(teacherId);
		
		TExam texam = texamService.getExam(texamId);
		model.addAttribute("texam", texam);
		
		TCourse tcourse = tcourseService.getTCourse(tcourseId);
		model.addAttribute("tcourse", tcourse);
	
		
		List<TStudent> tstudentList =  tstudentService.getTStudentByTCourse(tcourse);

		if(tstudentList.isEmpty())
		{
			model.addAttribute("noRegistration", true);
		}
		
		ListIterator<TStudent> iter = tstudentList.listIterator();
		
		while(iter.hasNext()){
			TStudent tstudent = iter.next();
			TMark tmark = tstudent.getTmark();
			
			if(tmark==null || tmark.getExternalMark()==null || tmark.getInternalMark()==null)
			{
				iter.remove();
				continue;
			}
			
			double internalNormalized = tmark.getInternalMark()*100; 
			internalNormalized /= (tcourse.getTotalFinalMark());
			
			double externalNormalized = tmark.getExternalMark()*100; 
			externalNormalized /= (tcourse.getTotalFinalMark());
		
			double diff = Math.abs(externalNormalized-internalNormalized);
	
			if(Double.compare(diff, Double.valueOf(20.0)) <= 0)
			{
				iter.remove();
			}
		    
		}

				
		Collections.sort(tstudentList);
	
		 try {
	         response.setContentType("application/vnd.ms-excel");
	         
	         String fileName = "attachment; filename=ThirdExaminerMark_" + tcourse.getTcourseCode() +"_" + texam.getTexamName() + "(" + texam.getSession() + ".xlsx";
	         response.setHeader("Content-Disposition", fileName);
	         XSSFWorkbook workbook = writeExcel(tstudentList,tcourse,texam);
	         workbook.write(response.getOutputStream());
	     } catch (Exception e) {
	         throw new ServletException("Exception in DownLoad Excel Servlet", e);
	     }
	
		  
		  
	}

	
	/////////////////////////////////////////
		
	private XSSFWorkbook writeExcel(List<TStudent> tstudentList, TCourse tcourse, TExam texam) throws IOException{
	
		XSSFWorkbook markEntryWorkBook = new XSSFWorkbook();
		XSSFSheet sheet = markEntryWorkBook.createSheet("MarkEntrySheet");
	
		
	    
	    XSSFFont font= markEntryWorkBook.createFont();
	    font.setFontHeightInPoints((short)9);
	    font.setFontName("Arial");
	    font.setColor(IndexedColors.BLACK.getIndex());
	    font.setBold(true);
	    font.setItalic(false);
	    
	    XSSFFont fontdef = markEntryWorkBook.createFont();
	    fontdef.setBold(false);
	    fontdef.setItalic(false);

	    CellStyle csdef =markEntryWorkBook.createCellStyle();
	    csdef.setFont(fontdef);
	    csdef.setAlignment(CellStyle.ALIGN_CENTER);
	    
	    
	    CellStyle cs=markEntryWorkBook.createCellStyle();
		  cs.setFillBackgroundColor(IndexedColors.RED.getIndex());
		  cs.setFillPattern(HSSFCellStyle.BIG_SPOTS);
		  cs.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		  cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		  cs.setFont(font);
		  cs.setAlignment(CellStyle.ALIGN_CENTER);
		  cs.setWrapText(true);
	    
	    
	    int rowCount = 4;
	   		
		  
		Row row = sheet.createRow(0);
		row.createCell(0); row.createCell(1);row.createCell(2);
		for(int i=0; i<=2; i++) row.getCell(i).setCellStyle(cs);
		row.getCell(0).setCellValue("Course Number");
		row.getCell(1).setCellValue("Credit");
		row.getCell(2).setCellValue("Course Title");

	  
		row = sheet.createRow(1);
		row.createCell(0); row.createCell(1);row.createCell(2);
		for(int i=0; i<=2; i++) row.getCell(i).setCellStyle(cs);
		row.getCell(0).setCellValue(tcourse.getTcourseCode());
		row.getCell(1).setCellValue(tcourse.getTcourseCredit());
		row.getCell(2).setCellValue(tcourse.getTcourseName());
		
		row = sheet.createRow(3);
		row.createCell(0); row.createCell(1);row.createCell(2);row.createCell(3);
		for(int i=0; i<=3; i++) row.getCell(i).setCellStyle(cs);
		row.getCell(0).setCellValue("Registration No");
		row.getCell(1).setCellValue("Class Roll");
		row.getCell(2).setCellValue("Exam Roll");
		row.getCell(3).setCellValue("Obtained Mark");
		for(int i=0; i<2; i++) row.getCell(i).setCellStyle(cs);
	
		    
		    for(int i = 0; i < tstudentList.size(); i++)
		    {
		    	Row newRow = sheet.createRow(rowCount++);
		    	
		    	for(int cellId=0; cellId<=4; cellId++)
		 	    {
		    		Cell cell = newRow.createCell(cellId);
		    		cell.setCellStyle(csdef);
		    		if(cellId==0)
		    		{	    			
		    			cell.setCellValue(tstudentList.get(i).getRegNumber());
		    		}
		    		else if(cellId==1)
		    		{
		    			cell.setCellValue(tstudentList.get(i).getClassRoll());
		    		}
		    		else if(cellId==2)
		    		{
		    			cell.setCellValue(tstudentList.get(i).getExamRoll());
		    		}
		 	    }
		    }
		    
		    for(int i=0; i<=3; i++)
			{
				sheet.autoSizeColumn(i);
			}
		    
		    return markEntryWorkBook;
		}
	





////////////////////////////////////////////
	



@PostMapping(value="/tabulation/{teacherId}/exam/{texamId}/course/{tcourseId}/tutorialMarkEntryExcelUpload")
public String tutorialExcelUp(@RequestParam("file") MultipartFile excelfile,Model model, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId, @PathVariable("tcourseId") long tcourseId) throws IOException, ServletException
{
	model.addAttribute("teacherId", teacherId);
	Teacher teacher = teacherService.getTeacher(teacherId);
	
	TExam texam = texamService.getExam(texamId);
	model.addAttribute("texam", texam);
	
	TCourse tcourse = tcourseService.getTCourse(tcourseId);
	model.addAttribute("tcourse", tcourse);
	
	Set<TStudent> tstudentSet =  tcourse.getTstudents();
	List<TStudent> tstudentList = new ArrayList<>(tstudentSet);
	Collections.sort(tstudentList);
	
	XSSFWorkbook workbook = new XSSFWorkbook(excelfile.getInputStream());

	XSSFSheet worksheet = workbook.getSheetAt(0);
	
	int initRow = 4;
	
	 for(int i=initRow;i<initRow+tstudentList.size();i++)
	 {
		 	System.out.println(i);
	        XSSFRow row = worksheet.getRow(i);
	        
	        Long examRoll = (long) row.getCell(2).getNumericCellValue();
			TStudent tstudentDb = tstudentService.getTStudentByExamRollAndTcourse(examRoll,  tcourse);
				
			TMark tmarkDb = tstudentDb.getTmark();
				
			Double tutoMark = Double.parseDouble((row.getCell(3).toString()));
				
			if(tmarkDb==null)
			{
				TMark tmark = new TMark();
				tmark.setTstudent(tstudentDb);
				tmark.setTutorialMark(tutoMark);
				tmarkService.addMarks(tmark);
				
				tmarkService.updateAllMarks(tmark);
			}
			else
			{
				tmarkDb.setTutorialMark(tutoMark);
				tmarkService.addMarks(tmarkDb);
				
				tmarkService.updateAllMarks(tmarkDb);
			}
				
	 }
			
	tstudentList =  tstudentService.getTStudentByTCourse(tcourse);
	
	if(tstudentList.isEmpty())
	{
		model.addAttribute("noRegistration", true);
	}

	ListIterator<TStudent> iter = tstudentList.listIterator();
	
	while(iter.hasNext()){
		TStudent tstudent = iter.next();
		TMark tmark = tstudent.getTmark();
		
		if(tmark==null || tmark.getExternalMark()==null || tmark.getInternalMark()==null)
		{
			iter.remove();
			continue;
		}
		
		double internalNormalized = tmark.getInternalMark()*100; 
		internalNormalized /= (tcourse.getTotalFinalMark());
		
		double externalNormalized = tmark.getExternalMark()*100; 
		externalNormalized /= (tcourse.getTotalFinalMark());
	
		double diff = Math.abs(externalNormalized-internalNormalized);

		if(Double.compare(diff, Double.valueOf(20.0)) <= 0)
		{
			iter.remove();
		}
	    
	}

	
	if(tstudentList.isEmpty()) 
	{
		model.addAttribute("thirdButtonDisabled", true);
		model.addAttribute("thirdButtonEnabled", false);
	}
	else 
	{
		model.addAttribute("thirdButtonDisabled", false);
		model.addAttribute("thirdButtonEnabled", true);
	}
	
	if(texam.isResultPublished()) 
	{
		model.addAttribute("updateLocked", true);
	}
		
	
	 return "tabulation/ea-course-config-page";
}



@PostMapping(value="/tabulation/{teacherId}/exam/{texamId}/course/{tcourseId}/internalMarkEntryExcelUpload")
public String internalExcelUp(@RequestParam("file") MultipartFile excelfile,Model model, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId, @PathVariable("tcourseId") long tcourseId) throws IOException, ServletException
{
	model.addAttribute("teacherId", teacherId);
	Teacher teacher = teacherService.getTeacher(teacherId);
	
	TExam texam = texamService.getExam(texamId);
	model.addAttribute("texam", texam);
	
	TCourse tcourse = tcourseService.getTCourse(tcourseId);
	model.addAttribute("tcourse", tcourse);
	
	Set<TStudent> tstudentSet =  tcourse.getTstudents();
	List<TStudent> tstudentList = new ArrayList<>(tstudentSet);
	Collections.sort(tstudentList);
	
	XSSFWorkbook workbook = new XSSFWorkbook(excelfile.getInputStream());

	XSSFSheet worksheet = workbook.getSheetAt(0);
	
	int initRow = 4;
	
	 for(int i=initRow;i<initRow+tstudentList.size();i++)
	 {
		 	System.out.println(i);
	        XSSFRow row = worksheet.getRow(i);
	        
	        Long examRoll = (long) row.getCell(2).getNumericCellValue();
			TStudent tstudentDb = tstudentService.getTStudentByExamRollAndTcourse(examRoll,  tcourse);
				
			TMark tmarkDb = tstudentDb.getTmark();
				
			Double tutoMark = Double.parseDouble((row.getCell(3).toString()));
				
			if(tmarkDb==null)
			{
				TMark tmark = new TMark();
				tmark.setTstudent(tstudentDb);
				tmark.setInternalMark(tutoMark);
				tmarkService.addMarks(tmark);
				
				tmarkService.updateAllMarks(tmark);
			}
			else
			{
				tmarkDb.setInternalMark(tutoMark);
				tmarkService.addMarks(tmarkDb);
				
				tmarkService.updateAllMarks(tmarkDb);
			}
				
	 }
			
	tstudentList =  tstudentService.getTStudentByTCourse(tcourse);
	
	if(tstudentList.isEmpty())
	{
		model.addAttribute("noRegistration", true);
	}

	ListIterator<TStudent> iter = tstudentList.listIterator();
	
	while(iter.hasNext()){
		TStudent tstudent = iter.next();
		TMark tmark = tstudent.getTmark();
		
		if(tmark==null || tmark.getExternalMark()==null || tmark.getInternalMark()==null)
		{
			iter.remove();
			continue;
		}
		
		double internalNormalized = tmark.getInternalMark()*100; 
		internalNormalized /= (tcourse.getTotalFinalMark());
		
		double externalNormalized = tmark.getExternalMark()*100; 
		externalNormalized /= (tcourse.getTotalFinalMark());
	
		double diff = Math.abs(externalNormalized-internalNormalized);

		if(Double.compare(diff, Double.valueOf(20.0)) <= 0)
		{
			iter.remove();
		}
	    
	}

	
	if(tstudentList.isEmpty()) 
	{
		model.addAttribute("thirdButtonDisabled", true);
		model.addAttribute("thirdButtonEnabled", false);
	}
	else 
	{
		model.addAttribute("thirdButtonDisabled", false);
		model.addAttribute("thirdButtonEnabled", true);
	}
	
	if(texam.isResultPublished()) 
	{
		model.addAttribute("updateLocked", true);
	}
		
	
	 return "tabulation/ea-course-config-page";
}


@PostMapping(value="/tabulation/{teacherId}/exam/{texamId}/course/{tcourseId}/externalMarkEntryExcelUpload")
public String externalExcelUp(@RequestParam("file") MultipartFile excelfile,Model model, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId, @PathVariable("tcourseId") long tcourseId) throws IOException, ServletException
{
	model.addAttribute("teacherId", teacherId);
	Teacher teacher = teacherService.getTeacher(teacherId);
	
	TExam texam = texamService.getExam(texamId);
	model.addAttribute("texam", texam);
	
	TCourse tcourse = tcourseService.getTCourse(tcourseId);
	model.addAttribute("tcourse", tcourse);
	
	Set<TStudent> tstudentSet =  tcourse.getTstudents();
	List<TStudent> tstudentList = new ArrayList<>(tstudentSet);
	Collections.sort(tstudentList);
	
	XSSFWorkbook workbook = new XSSFWorkbook(excelfile.getInputStream());

	XSSFSheet worksheet = workbook.getSheetAt(0);
	
	int initRow = 4;
	
	 for(int i=initRow;i<initRow+tstudentList.size();i++)
	 {
	        XSSFRow row = worksheet.getRow(i);
	        
	        Long examRoll = (long) row.getCell(2).getNumericCellValue();
			TStudent tstudentDb = tstudentService.getTStudentByExamRollAndTcourse(examRoll,  tcourse);
				
			TMark tmarkDb = tstudentDb.getTmark();
				
			Double tutoMark = Double.parseDouble((row.getCell(3).toString()));
				
			if(tmarkDb==null)
			{
				TMark tmark = new TMark();
				tmark.setTstudent(tstudentDb);
				tmark.setExternalMark(tutoMark);
				tmarkService.addMarks(tmark);
				
				tmarkService.updateAllMarks(tmark);
			}
			else
			{
				tmarkDb.setExternalMark(tutoMark);
				tmarkService.addMarks(tmarkDb);
				
				tmarkService.updateAllMarks(tmarkDb);
			}
				
	 }
			
	tstudentList =  tstudentService.getTStudentByTCourse(tcourse);
	
	if(tstudentList.isEmpty())
	{
		model.addAttribute("noRegistration", true);
	}

	ListIterator<TStudent> iter = tstudentList.listIterator();
	
	while(iter.hasNext()){
		TStudent tstudent = iter.next();
		TMark tmark = tstudent.getTmark();
		
		if(tmark==null || tmark.getExternalMark()==null || tmark.getInternalMark()==null)
		{
			iter.remove();
			continue;
		}
		
		double internalNormalized = tmark.getInternalMark()*100; 
		internalNormalized /= (tcourse.getTotalFinalMark());
		
		double externalNormalized = tmark.getExternalMark()*100; 
		externalNormalized /= (tcourse.getTotalFinalMark());
	
		double diff = Math.abs(externalNormalized-internalNormalized);

		if(Double.compare(diff, Double.valueOf(20.0)) <= 0)
		{
			iter.remove();
		}
	    
	}

	
	if(tstudentList.isEmpty()) 
	{
		model.addAttribute("thirdButtonDisabled", true);
		model.addAttribute("thirdButtonEnabled", false);
	}
	else 
	{
		model.addAttribute("thirdButtonDisabled", false);
		model.addAttribute("thirdButtonEnabled", true);
	}
	
	if(texam.isResultPublished()) 
	{
		model.addAttribute("updateLocked", true);
	}
		
	
	 return "tabulation/ea-course-config-page";
}


@PostMapping(value="/tabulation/{teacherId}/exam/{texamId}/course/{tcourseId}/thirdExaminerMarkEntryExcelUpload")
public String thirdExaminerExcelUp(@RequestParam("file") MultipartFile excelfile,Model model, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId, @PathVariable("tcourseId") long tcourseId) throws IOException, ServletException
{
	model.addAttribute("teacherId", teacherId);
	Teacher teacher = teacherService.getTeacher(teacherId);
	
	TExam texam = texamService.getExam(texamId);
	model.addAttribute("texam", texam);
	
	TCourse tcourse = tcourseService.getTCourse(tcourseId);
	model.addAttribute("tcourse", tcourse);
	
//	Set<TStudent> tstudentSet =  tcourse.getTstudents();
//	List<TStudent> tstudentList = new ArrayList<>(tstudentSet);
//	Collections.sort(tstudentList);
	List<TStudent> tstudentList =  tstudentService.getTStudentByTCourse(tcourse);
	
	ListIterator<TStudent> iter = tstudentList.listIterator();
	
	while(iter.hasNext()){
		TStudent tstudent = iter.next();
		TMark tmark = tstudent.getTmark();
		
		if(tmark==null || tmark.getExternalMark()==null || tmark.getInternalMark()==null)
		{
			iter.remove();
			continue;
		}
		
		double internalNormalized = tmark.getInternalMark()*100; 
		internalNormalized /= (tcourse.getTotalFinalMark());
		
		double externalNormalized = tmark.getExternalMark()*100; 
		externalNormalized /= (tcourse.getTotalFinalMark());
	
		double diff = Math.abs(externalNormalized-internalNormalized);

		if(Double.compare(diff, Double.valueOf(20.0)) <= 0)
		{
			iter.remove();
		}
	    
	}
	
	
	XSSFWorkbook workbook = new XSSFWorkbook(excelfile.getInputStream());

	XSSFSheet worksheet = workbook.getSheetAt(0);
	
	int initRow = 4;
	
	 for(int i=initRow;i<initRow+tstudentList.size();i++)
	 {
	        XSSFRow row = worksheet.getRow(i);
	        
	        Long examRoll = (long) row.getCell(2).getNumericCellValue();
			TStudent tstudentDb = tstudentService.getTStudentByExamRollAndTcourse(examRoll,  tcourse);
				
			TMark tmarkDb = tstudentDb.getTmark();
				
			Double thirdExaminerMark = Double.parseDouble((row.getCell(3).toString()));
			
			System.out.println(examRoll +  " " + thirdExaminerMark);
			
			if(tmarkDb==null)
			{
				TMark tmark = new TMark();
				tmark.setTstudent(tstudentDb);
				tmark.setThirdExaminerMark(thirdExaminerMark);
				tmarkService.addMarks(tmark);
				
				tmarkService.updateAllMarks(tmark);
			}
			else
			{
				tmarkDb.setThirdExaminerMark(thirdExaminerMark);
				tmarkService.addMarks(tmarkDb);
				
				tmarkService.updateAllMarks(tmarkDb);
			}
				
	 }
			

	if(tstudentList.isEmpty())
	{
		model.addAttribute("noRegistration", true);
	}


	if(tstudentList.isEmpty()) 
	{
		model.addAttribute("thirdButtonDisabled", true);
		model.addAttribute("thirdButtonEnabled", false);
	}
	else 
	{
		model.addAttribute("thirdButtonDisabled", false);
		model.addAttribute("thirdButtonEnabled", true);
	}
	
	if(texam.isResultPublished()) 
	{
		model.addAttribute("updateLocked", true);
	}
		
	
	 return "tabulation/ea-course-config-page";
}

}
