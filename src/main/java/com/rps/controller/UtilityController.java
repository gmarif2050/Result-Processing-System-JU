package com.rps.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rps.dto.tabulation.MarkEntity;
import com.rps.entities.storage.Student;
import com.rps.entities.tabulation.TCourse;
import com.rps.entities.tabulation.TExam;
import com.rps.entities.tabulation.TMark;
import com.rps.entities.tabulation.TStudent;
import com.rps.entities.tabulation.Teacher;
import com.rps.service.storage.StudentService;
import com.rps.service.tabulation.TCourseService;
import com.rps.service.tabulation.TExamService;
import com.rps.service.tabulation.TStudentService;
import com.rps.service.tabulation.TeacherService;
import com.rps.utility.tabulation.UtilityService;

@Controller
public class UtilityController {
	
	
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
	
	@RequestMapping(value="/tabulation/{teacherId}/exam/{texamId}/course/{tcourseId}/printThirdExaminerPapers", method=RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    ResponseEntity<InputStreamResource> printThirdExaminer(@PathVariable Long teacherId, @PathVariable Long texamId, @PathVariable Long tcourseId) throws IOException, DocumentException, ParseException {
		
		Teacher teacher = teacherService.getTeacher(teacherId);
		
		TExam texam = texamService.getExam(texamId);

		
		TCourse tcourse = tcourseService.getTCourse(tcourseId);

		
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
		
		Collections.sort(tstudentList);
		
        // Written seperate method
        ByteArrayInputStream byteArrayInputStream = generateThirdExamPapers(texam, tcourse, tstudentList);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=marksheet.pdf");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.LETTER, 20, 20, 50, 25);
         PdfWriter.getInstance(document, bos);
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(byteArrayInputStream));
    }


	public ByteArrayInputStream generateThirdExamPapers(TExam texam, TCourse tcourse, List<TStudent> tstudentList)  throws MalformedURLException, IOException{
		
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {

            // For font style
            Font headFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 10);
            List<String> headers = Arrays.asList("hah");
//            PdfPTable table = new PdfPTable(headers.size());
//            table.setWidthPercentage(90);
//            table.setWidths(new int[]{5});

//            // Setting headers
//            for (String string : headers) {
//                PdfPCell hcell;
//                hcell = new PdfPCell(new Phrase(string, headFont));
//                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                table.addCell(hcell);
//            }

//            int count = 0;
//
//            for (TStudent tstudent: tstudentList) {
//                //count ++ ;
//
//                Font font = FontFactory.getFont(FontFactory.COURIER, 8);
//                PdfPCell cell;
//                cell = new PdfPCell(new Phrase(String.valueOf(count), font));
//                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase(mark.getCourseCode(), font));
//                cell.setPaddingLeft(2);
//                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase(mark.getCourseName(), font));
//                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell.setPaddingRight(2);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase(String.valueOf(mark.getMarkAchievedby100()), font));
//                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell.setPaddingRight(2);
//                table.addCell(cell);
//
//                cell = new PdfPCell(new Phrase(String.valueOf(mark.getGradePoint()), font));
//                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell.setPaddingRight(2);
//                table.addCell(cell);
//                
//                cell = new PdfPCell(new Phrase(String.valueOf(mark.getNoOfCredit()), font));
//                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell.setPaddingRight(2);
//                table.addCell(cell);
//            }
            
            Font font = FontFactory.getFont(FontFactory.COURIER, 8);
//            PdfPCell cell = new PdfPCell(new Phrase("", font));
//            table.addCell(cell);
       
            
            
            
            PdfWriter.getInstance(document, out);
            document.open();
            Paragraph para = new Paragraph();
            
            
            Font fontHeaderUnderLined = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.UNDERLINE);
            Font fontHeader = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
            Font font1 = new Font(Font.FontFamily.COURIER, 10, Font.BOLD);

            
            Image logo = Image.getInstance("src/main/resources/static/media/image/ju_logo_original_v2.png");
            logo.scaleAbsolute(50f, 50f);
            logo.scaleToFit(60f, 60f);
            logo.setAbsolutePosition(85, 745);
            document.add(logo);
         
            
            para = new Paragraph("Jahangirnagar University", font1);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            
            para = new Paragraph("Department of " + tcourse.getTexam().getTeacher().getDept(), fontHeader);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            
            document.add( Chunk.NEWLINE );

            para = new Paragraph(texam.getProgramCode() + " " + texam.getTexamName(), headFont);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            
            document.add( Chunk.NEWLINE );

            para = new Paragraph("Course Title: " + tcourse.getTcourseName(), headFont);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);

            para = new Paragraph("Course Code: " + tcourse.getTcourseCode(), headFont);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            
            
            para = new Paragraph("Course Credit: " + tcourse.getTcourseCredit(), headFont);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            
            document.add( Chunk.NEWLINE );
            
            para = new Paragraph("Thrid Examiner Papers", fontHeaderUnderLined);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            
            document.add( Chunk.NEWLINE );

            
//            Chunk underline = new Chunk("Thrid Examiner Papers");
//            underline.setUnderline(0.1f, -2f); //0.1 thick, -2 y-location
//            document.add(underline);
//            
            
            for(TStudent tstudent: tstudentList)
            {
            	 para = new Paragraph(tstudent.getExamRoll().toString(), font1);
                 para.setAlignment(Element.ALIGN_CENTER);
                 document.add(para);
            }
            
            
           // document.add(table);
            document.close();
        } catch (DocumentException ex) {
            Logger.getLogger("PDF Download").log(Level.SEVERE, null, ex);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

	
	@RequestMapping(value="/tabulation/{teacherId}/exam/{texamId}/course/{tcourseId}/printAverageSheet", method=RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    ResponseEntity<InputStreamResource> AvgSheetPrint(@PathVariable Long teacherId, @PathVariable Long texamId, @PathVariable Long tcourseId) throws IOException, DocumentException, ParseException {
		
		Teacher teacher = teacherService.getTeacher(teacherId);
		
		TExam texam = texamService.getExam(texamId);
		
		TCourse tcourse = tcourseService.getTCourse(tcourseId);
		
		Set<TStudent> tstudentSet =  tcourse.getTstudents();
		
		List<TStudent> tstudentList = new ArrayList<>(tstudentSet);
		Collections.sort(tstudentList);
		
        // Written seperate method
        ByteArrayInputStream byteArrayInputStream = generatePdfAvgSheet(texam, tcourse, tstudentList);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=averageSheet.pdf");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.LETTER, 20, 20, 50, 25);
         PdfWriter.getInstance(document, bos);
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(byteArrayInputStream));
    }
	
	
	
	public ByteArrayInputStream generatePdfAvgSheet(TExam texam, TCourse tcourse, List<TStudent> tstudentList)  throws MalformedURLException, IOException{
		
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {

            Font headFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 10);
            List<String> headers = Arrays.asList("Serial", "Exam Roll", "Internal", "External", "Difference", "Third Exam" , "Average Mark");
            PdfPTable table = new PdfPTable(headers.size());
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1,2,2,2,2,2,2});
             
            for (String string : headers) {
                PdfPCell hcell;
                hcell = new PdfPCell(new Phrase(string, headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(hcell);
            }

            int count = 0;

            for (TStudent tstudent: tstudentList) {
                count ++ ;
                
                TMark tmark = tstudent.getTmark();
                if(tmark==null) continue;
                
                Font font = FontFactory.getFont(FontFactory.COURIER, 9);
                PdfPCell cell;
                cell = new PdfPCell(new Phrase(String.valueOf(count), font));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(tstudent.getExamRoll().toString(), font));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(2);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(tmark.getInternalMark()), font));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(2);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(tmark.getExternalMark()), font));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(2);
                table.addCell(cell);
                
                Double diffInEx = Math.abs(tmark.getExternalMark() - tmark.getInternalMark());
                
                cell = new PdfPCell(new Phrase(diffInEx.toString(), font));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(2);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase( (tmark.getThirdExaminerMark()==null) ? Integer.valueOf(0).toString() : tmark.getThirdExaminerMark().toString(), font));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(2);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(String.valueOf(tmark.getFinalMark()), font));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(2);
                table.addCell(cell);
                
            }
            
            Font font = FontFactory.getFont(FontFactory.COURIER, 8);
//            PdfPCell cell = new PdfPCell(new Phrase("", font));
//            table.addCell(cell);
//       
            
            
            
            PdfWriter.getInstance(document, out);
            document.open();
            Paragraph para = new Paragraph();
            
            
            Font fontHeaderUnderLined = new Font(Font.FontFamily.HELVETICA, 11, Font.UNDERLINE);
            Font fontHeader = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
            Font font1 = new Font(Font.FontFamily.COURIER, 10, Font.BOLD);

            
            Image logo = Image.getInstance("src/main/resources/static/media/image/ju_logo_original_v2.png");
            logo.scaleAbsolute(50f, 50f);
            logo.scaleToFit(60f, 60f);
            logo.setAbsolutePosition(85, 745);
            document.add(logo);
         
            para = new Paragraph("Jahangirnagar University", font1);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            
            para = new Paragraph("Department of " + tcourse.getTexam().getTeacher().getDept(), fontHeader);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            
         //   document.add( Chunk.NEWLINE );

            para = new Paragraph(texam.getProgramCode() + " " + texam.getTexamName(), headFont);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            
            document.add( Chunk.NEWLINE );

            para = new Paragraph("Course Title: " + tcourse.getTcourseName(), headFont);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);

            para = new Paragraph("Course Code: " + tcourse.getTcourseCode(), headFont);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            
            
            para = new Paragraph("Course Credit: " + tcourse.getTcourseCredit(), headFont);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            
            document.add( Chunk.NEWLINE );
            
            para = new Paragraph("AVERAGE SHEET", fontHeaderUnderLined);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            
            document.add( Chunk.NEWLINE );

            
//            Chunk underline = new Chunk("Thrid Examiner Papers");
//            underline.setUnderline(0.1f, -2f); //0.1 thick, -2 y-location
//            document.add(underline);
//            

            
            
            document.add(table);
            document.close();
        } catch (DocumentException ex) {
            Logger.getLogger("PDF Download").log(Level.SEVERE, null, ex);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
	
	
	
	
	//////////////////////////
	@RequestMapping(value="/tabulation/{teacherId}/exam/{texamId}/printMarkSheet/{examRoll}", method=RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    ResponseEntity<InputStreamResource> MarkSheetPrint(@PathVariable Long teacherId, @PathVariable Long texamId, @PathVariable Long examRoll) throws IOException, DocumentException, ParseException {
		
List<TStudent> tstudentList = tstudentService.getTStudentByExamRoll(examRoll);
		
		List<MarkEntity> markList = new ArrayList<>();
		
		TExam texam = texamService.getExam(texamId);
		Set<TCourse> tcourseSet = texam.getTcourses();
		
		Double totalCreditHr = 0.0;
		Double totalWeightedGradePoint = 0.0; 
		
		for(TStudent tstudent : tstudentList)
		{
			if(tstudent.getTcourse().getTexam().getTexamId() != texam.getTexamId()) continue;
			
			MarkEntity mark = new MarkEntity();
			
			TCourse tcourse = tstudent.getTcourse();
			TMark tmark = tstudent.getTmark();
			if(tmark==null) continue;

			totalCreditHr += tcourse.getTcourseCredit();
			totalWeightedGradePoint += (tmark.getGradePoint() * tcourse.getTcourseCredit());
			
			mark.setCourseName(tcourse.getTcourseName());
			mark.setCourseNumber(tcourse.getTcourseCode());
			mark.setCourseCredit(tcourse.getTcourseCredit());
			
			mark.setTutorialMark(tmark.getTutorialMark());
			mark.setInternalMark(tmark.getInternalMark());
			mark.setExternalMark(tmark.getExternalMark());
			mark.setThirdExaminerMark(tmark.getThirdExaminerMark());
			mark.setFinalMark(tmark.getFinalMark());
			mark.setTotalMark(tmark.getTotalMark());
			mark.setGradePoint(tmark.getGradePoint());
			mark.setLetterGrade(tmark.getLetterGrade());
			mark.setRemarks(tmark.getRemarks());
			
			markList.add(mark);
		}	
		
		Collections.sort(markList);
		
		Double gpa = 0.0;
		
		if(markList.size() == tcourseSet.size())
		{
			gpa = totalWeightedGradePoint/totalCreditHr;
		}

		
		gpa = UtilityService.round(gpa, 2);
		
		
		Student student = studentService.getStudentByExamRoll(examRoll);
		
        // Written seperate method
        ByteArrayInputStream byteArrayInputStream = generatePdfMarkSheet(texam, student, markList, gpa, tstudentList);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=markSheet.pdf");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.LETTER_LANDSCAPE, 20, 20, 50, 25);
         PdfWriter.getInstance(document, bos);
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(byteArrayInputStream));
    }
	
	
	
	public ByteArrayInputStream generatePdfMarkSheet(TExam texam,Student student,List<MarkEntity> markList,Double gpa,List<TStudent> tstudentList)  throws MalformedURLException, IOException{
		
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {

            Font headFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 10);
            List<String> headers = Arrays.asList("Code", "Name", "Credit", "Tutorial", "Internal", "External", "Third Exam" , "Avg Mark", "Grand Total", "GP", "LG", "Remarks");
            PdfPTable table = new PdfPTable(headers.size());
            table.setWidthPercentage(100);
            table.setWidths(new int[]{2,4,3,3,3,3,2,2,2,2,1,3});
             
            for (String string : headers) {
                PdfPCell hcell;
                hcell = new PdfPCell(new Phrase(string, headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(hcell);
            }

            int count = 0;

            for (MarkEntity mark: markList) {
                count ++ ;
                
                Font font = FontFactory.getFont(FontFactory.COURIER, 9);
                PdfPCell cell;

                cell = new PdfPCell(new Phrase(mark.getCourseNumber().toString(), font));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(2);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(mark.getCourseName()), font));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(2);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(mark.getCourseCredit()), font));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(2);
                table.addCell(cell);
                
            
                cell = new PdfPCell(new Phrase((mark.getTutorialMark()==null) ? Integer.valueOf(0).toString() : mark.getTutorialMark().toString(), font));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(2);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase((mark.getInternalMark()==null) ? Integer.valueOf(0).toString() : mark.getInternalMark().toString(), font));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(2);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase((mark.getExternalMark()==null) ? Integer.valueOf(0).toString() : mark.getExternalMark().toString(), font));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(2);
                table.addCell(cell);
                
                
                cell = new PdfPCell(new Phrase( (mark.getThirdExaminerMark()==null) ? Integer.valueOf(0).toString() : mark.getThirdExaminerMark().toString(), font));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(2);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(String.valueOf(mark.getFinalMark()), font));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(2);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(String.valueOf(mark.getTotalMark()), font));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(2);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(String.valueOf(mark.getGradePoint()), font));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(2);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(String.valueOf(mark.getLetterGrade()), font));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(2);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(String.valueOf(mark.getRemarks()), font));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingRight(2);
                table.addCell(cell);
                
            }
            
            Font font = FontFactory.getFont(FontFactory.COURIER, 8);
//            PdfPCell cell = new PdfPCell(new Phrase("", font));
//            table.addCell(cell);
//       
            
            PdfWriter.getInstance(document, out);
            document.open();
            Paragraph para = new Paragraph();
            
            
            Font fontHeaderUnderLined = new Font(Font.FontFamily.HELVETICA, 11, Font.UNDERLINE);
            Font fontHeader = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
            Font font1 = new Font(Font.FontFamily.COURIER, 10, Font.BOLD);

            
            Image logo = Image.getInstance("src/main/resources/static/media/image/ju_logo_original_v2.png");
            logo.scaleAbsolute(50f, 50f);
            logo.scaleToFit(60f, 60f);
            logo.setAbsolutePosition(85, 745);
            document.add(logo);
         
            para = new Paragraph("Jahangirnagar University", font1);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            
            para = new Paragraph("Department of " + texam.getTeacher().getDept(), fontHeader);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            
         //   document.add( Chunk.NEWLINE );

            para = new Paragraph(texam.getProgramCode() + " " + texam.getTexamName(), headFont);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            
            document.add( Chunk.NEWLINE );

            para = new Paragraph("Name : " + student.getName(), headFont);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);

            para = new Paragraph("Exam Roll: " + student.getExamRoll(), headFont);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            
            
            para = new Paragraph("Registration No: " + student.getRegNumber(), headFont);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            
            
            document.add( Chunk.NEWLINE );
            
            para = new Paragraph("Tabulated Mark Sheet", fontHeaderUnderLined);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            
            document.add( Chunk.NEWLINE );
            
            para = new Paragraph("Secured GPA: "+gpa, headFont);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            
            document.add( Chunk.NEWLINE );
            
//            Chunk underline = new Chunk("Thrid Examiner Papers");
//            underline.setUnderline(0.1f, -2f); //0.1 thick, -2 y-location
//            document.add(underline);
//            

            
            
            document.add(table);
            document.close();
        } catch (DocumentException ex) {
            Logger.getLogger("PDF Download").log(Level.SEVERE, null, ex);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
	
}
