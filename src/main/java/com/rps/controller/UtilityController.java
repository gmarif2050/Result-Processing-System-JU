//package com.rps.controller;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.text.ParseException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.FontFactory;
//import com.itextpdf.text.Image;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.Phrase;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.rps.dao.storage.FinalExamDao;
//import com.rps.dao.storage.CourseDao;
//import com.rps.dao.storage.StudentDao;
//import com.rps.entities.storage.FinalExam;
//import com.rps.entities.storage.Course;
//import com.rps.entities.storage.Student;
//
//@Controller
//public class UtilityController {
//	
//	@Autowired
//	FinalExamDao finalExamDao;
//	@Autowired
//	CourseDao courseDao;
//	@Autowired
//	StudentDao studentDao;
//	
//	@RequestMapping(value="/showDetailDegrees/{regNumber}/{programId}/{finalExamId}/generate-pdf", method=RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
//    ResponseEntity<InputStreamResource> newsReport(@PathVariable Long finalExamId, @PathVariable String regNumber) throws IOException, DocumentException, ParseException {
//
//		Student student = studentDao.findByRegNumber(regNumber);
//		FinalExam finalExam = finalExamDao.findByExamId(finalExamId);
//		List<Course> marklist = courseDao.findByFinalExam(finalExam);
//		
//        // Written seperate method
//        ByteArrayInputStream byteArrayInputStream = employeeGeneratePdf(student, marklist, finalExam);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "inline; filename=marksheet.pdf");
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        Document document = new Document(PageSize.LETTER, 20, 20, 50, 25);
//         PdfWriter.getInstance(document, bos);
//        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(byteArrayInputStream));
//    }
//
//
//    public ByteArrayInputStream employeeGeneratePdf(Student student, List<Course> marklist, FinalExam finalExam) throws MalformedURLException, IOException {
//        Document document = new Document();
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        try {
//
//            // For font style
//            Font headFont = FontFactory.getFont(FontFactory.COURIER_BOLDOBLIQUE, 10);
//            List<String> headers = Arrays.asList("No", "Course Number", "Course Name", "Obtained Mark", "Grade Point", "Credit Hour");
//            PdfPTable table = new PdfPTable(headers.size());
//            table.setWidthPercentage(90);
//            table.setWidths(new int[]{1, 2, 4, 2, 2, 2});
//
//            // Setting headers
//            for (String string : headers) {
//                PdfPCell hcell;
//                hcell = new PdfPCell(new Phrase(string, headFont));
//                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                table.addCell(hcell);
//            }
//
//            int count = 0;
//
//            for (Course mark : marklist) {
//                count ++ ;
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
//            
//            Font font = FontFactory.getFont(FontFactory.COURIER, 8);
//            PdfPCell cell = new PdfPCell(new Phrase("", font));
//            table.addCell(cell);
//            table.addCell(cell);
//            table.addCell(cell);
//            table.addCell(cell);
//            
//            cell = new PdfPCell(new Phrase("Grade Point Avg.", font));
//            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cell.setPaddingRight(2);
//            table.addCell(cell);
//            
//            cell = new PdfPCell(new Phrase(String.valueOf(finalExam.getGPA()), font));
//            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cell.setPaddingRight(2);
//            table.addCell(cell);
//            
//            
//            PdfWriter.getInstance(document, out);
//            document.open();
//            Paragraph para = new Paragraph();
//            Font fontHeader = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
//            Font font1 = new Font(Font.FontFamily.COURIER, 10, Font.BOLD);
//
//            
//            Image logo = Image.getInstance("src/main/resources/static/media/image/ju_logo_original_v2.png");
//            logo.scaleAbsolute(50f, 50f);
//            logo.scaleToFit(60f, 60f);
//            logo.setAbsolutePosition(130, 745);
//            document.add(logo);
//            para = new Paragraph("Controller of Examination Office", fontHeader);
//            para.setAlignment(Element.ALIGN_CENTER);
//            document.add(para);
//            para = new Paragraph("Jahangirnagar University", font1);
//            para.setAlignment(Element.ALIGN_CENTER);
//            document.add(para);
//            
//            para = new Paragraph(finalExam.getProgram().getProgramCode() + " "+ finalExam.getExamName() +"  Examination", fontHeader);
//            para.setAlignment(Element.ALIGN_CENTER);
//            document.add(para);
//            
//            para = new Paragraph(" ", fontHeader);
//            document.add(para);
//            
////            para = new Paragraph("Student Information", font1);
////            para.setAlignment(Element.ALIGN_CENTER);
////            document.add(para);
//            
//            para = new Paragraph("Name: " + student.getName(), font1);
//            para.setAlignment(Element.ALIGN_CENTER);
//            document.add(para);
//            
//            para = new Paragraph("Father's Name: " + student.getFathersName(), font1);
//            para.setAlignment(Element.ALIGN_CENTER);
//            document.add(para);
//            
//            para = new Paragraph("Mother's Name: " + student.getMothersName(), font1);
//            para.setAlignment(Element.ALIGN_CENTER);
//            document.add(para);
//
//            para = new Paragraph("Registration No: " + student.getRegNumber(), font1);
//            para.setAlignment(Element.ALIGN_CENTER);
//            document.add(para);
//            
//            para = new Paragraph("Examination Roll: " + student.getExamRoll(), font1);
//            para.setAlignment(Element.ALIGN_CENTER);
//            document.add(para);
//            
//            para = new Paragraph("Session: " + student.getSession(), font1);
//            para.setAlignment(Element.ALIGN_CENTER);
//            document.add(para);
//            
//            para = new Paragraph("Department: " + student.getDept(), font1);
//            para.setAlignment(Element.ALIGN_CENTER);
//            document.add(para);
//            
//            
//            para = new Paragraph(" ", fontHeader);
//            document.add(para);
//            para = new Paragraph(" ", fontHeader);
//            document.add(para);
//            
//            document.add(table);
//            document.close();
//        } catch (DocumentException ex) {
//            Logger.getLogger("PDF Download").log(Level.SEVERE, null, ex);
//        }
//        return new ByteArrayInputStream(out.toByteArray());
//    }
//
//}
