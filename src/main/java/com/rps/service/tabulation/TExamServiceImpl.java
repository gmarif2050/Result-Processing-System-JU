package com.rps.service.tabulation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rps.dao.tabulation.TExamDao;
import com.rps.entities.storage.Course;
import com.rps.entities.tabulation.TCourse;
import com.rps.entities.tabulation.TExam;
import com.rps.entities.tabulation.TStudent;

@Transactional
@Service
public class TExamServiceImpl implements TExamService {

	@Autowired
	TExamDao texamDao;
	@Autowired
	TCourseService tcourseService;
	
	@Override
	public void addExam(TExam texam) {
		texamDao.save(texam);
	}

	@Override
	public void updateExamInfo(TExam texam) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Set<TCourse> findCourses(long texamId)
	{
		TExam texam = texamDao.findByTexamId(texamId);
		return texam.getTcourses();
	}

	@Override
	public TExam getExam(long examId) {
		return texamDao.findByTexamId(examId);
	}

	@Override
	public void removeTExam(TExam texam) {
		
		Set<TCourse> tcourseSet = texam.getTcourses();

		for(TCourse tcourse: tcourseSet)
		{
			tcourseService.removeCourse(tcourse);			
		}
		
		texamDao.delete(texam);
		
	}

	@Override
	public ByteArrayInputStream generatePdf(TExam texam, TCourse tcourse, List<TStudent> tstudentList)  throws MalformedURLException, IOException{
	
	        Document document = new Document();
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        try {

	            // For font style
	            Font headFont = FontFactory.getFont(FontFactory.COURIER_BOLDOBLIQUE, 10);
	            List<String> headers = Arrays.asList("");
	            PdfPTable table = new PdfPTable(headers.size());
	            table.setWidthPercentage(90);
	            table.setWidths(new int[]{1, 2, 4, 2, 2, 2});

//	            // Setting headers
//	            for (String string : headers) {
//	                PdfPCell hcell;
//	                hcell = new PdfPCell(new Phrase(string, headFont));
//	                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	                hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//	                table.addCell(hcell);
//	            }

//	            int count = 0;
//
//	            for (TStudent tstudent: tstudentList) {
//	                //count ++ ;
//
//	                Font font = FontFactory.getFont(FontFactory.COURIER, 8);
//	                PdfPCell cell;
//	                cell = new PdfPCell(new Phrase(String.valueOf(count), font));
//	                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//	                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	                table.addCell(cell);
//
//	                cell = new PdfPCell(new Phrase(mark.getCourseCode(), font));
//	                cell.setPaddingLeft(2);
//	                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//	                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	                table.addCell(cell);
//
//	                cell = new PdfPCell(new Phrase(mark.getCourseName(), font));
//	                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//	                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	                cell.setPaddingRight(2);
//	                table.addCell(cell);
//
//	                cell = new PdfPCell(new Phrase(String.valueOf(mark.getMarkAchievedby100()), font));
//	                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//	                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	                cell.setPaddingRight(2);
//	                table.addCell(cell);
//
//	                cell = new PdfPCell(new Phrase(String.valueOf(mark.getGradePoint()), font));
//	                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//	                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	                cell.setPaddingRight(2);
//	                table.addCell(cell);
//	                
//	                cell = new PdfPCell(new Phrase(String.valueOf(mark.getNoOfCredit()), font));
//	                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//	                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//	                cell.setPaddingRight(2);
//	                table.addCell(cell);
//	            }
	            
	            Font font = FontFactory.getFont(FontFactory.COURIER, 8);
	            PdfPCell cell = new PdfPCell(new Phrase("", font));
	            table.addCell(cell);
	            table.addCell(cell);
	            table.addCell(cell);
	            table.addCell(cell);
	            
	            
	            
	            PdfWriter.getInstance(document, out);
	            document.open();
	            Paragraph para = new Paragraph();
	            Font fontHeader = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	            Font font1 = new Font(Font.FontFamily.COURIER, 10, Font.BOLD);

	            
	            Image logo = Image.getInstance("src/main/resources/static/media/image/ju_logo_original_v2.png");
	            logo.scaleAbsolute(50f, 50f);
	            logo.scaleToFit(60f, 60f);
	            logo.setAbsolutePosition(130, 745);
	            document.add(logo);
	         
	            
	            para = new Paragraph("Jahangirnagar University", font1);
	            para.setAlignment(Element.ALIGN_CENTER);
	            document.add(para);
	            
	            para = new Paragraph(texam.getProgramCode() + " " + texam.getTexamName(), fontHeader);
	            para.setAlignment(Element.ALIGN_CENTER);
	            document.add(para);
	            
	            para = new Paragraph("Thrid Examiner Papers", fontHeader);
	            para.setAlignment(Element.ALIGN_CENTER);
	            document.add(para);
	            
	            para = new Paragraph("Department of " + tcourse.getTexam().getTeacher().getDept(), fontHeader);
	            document.add(para);

	            para = new Paragraph("Course Code: " + tcourse.getTcourseCode(), font1);
	            para.setAlignment(Element.ALIGN_CENTER);
	            document.add(para);
	            
	            para = new Paragraph("Course Name: " + tcourse.getTcourseName(), font1);
	            para.setAlignment(Element.ALIGN_CENTER);
	            document.add(para);
	            
	            
	            para = new Paragraph(" ", fontHeader);
	            document.add(para);
	            para = new Paragraph(" ", fontHeader);
	            document.add(para);
	            
	            document.add(table);
	            document.close();
	        } catch (DocumentException ex) {
	            Logger.getLogger("PDF Download").log(Level.SEVERE, null, ex);
	        }
	        return new ByteArrayInputStream(out.toByteArray());
	    }
	
	
	
}
