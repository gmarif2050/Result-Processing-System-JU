package com.rps.service.tabulation;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rps.dao.tabulation.TMarkDao;
import com.rps.entities.tabulation.TCourse;
import com.rps.entities.tabulation.TMark;
import com.rps.entities.tabulation.TStudent;
import com.rps.utility.tabulation.UtilityService;

@Transactional
@Service
public class TMarkServiceImpl implements TMarkService {

	@Autowired
	TMarkDao tmarkDao;
	@Autowired
	TStudentService tstudentService;
	
	@Override
	public void addMarks(TMark tmark) {
		
		tmarkDao.save(tmark);
		
	}

	@Override
	public TMark getTMarkByTStudent(TStudent tstudent) {
		
		return tmarkDao.findByTstudent(tstudent);
	}

	@Override
	public void removeTMark(TMark tmark) {
		if(tmark!=null)
			tmarkDao.delete(tmark);
	}

	@Override
	public void updateAllMarks(TMark tmark) {
		
		Double finalMark = tmark.getFinalMark();
		
		if(tmark.getThirdExaminerMark()==null)
		{
			if(tmark.getExternalMark()==null)
			{
				if(tmark.getInternalMark()!=null)
					finalMark = tmark.getInternalMark();
			}
			else 
			{
				if(tmark.getInternalMark()!=null && tmark.getExternalMark()!=null)
				{
					finalMark = (tmark.getInternalMark()+tmark.getExternalMark())/(2.0);
				}
			}
		}
		else 
		{
			if(tmark.getInternalMark()!=null && tmark.getExternalMark()!=null && tmark.getThirdExaminerMark()!=null)
			{
				
				double inDiff = Math.abs(tmark.getThirdExaminerMark() - tmark.getInternalMark());
				double exDiff = Math.abs(tmark.getThirdExaminerMark() - tmark.getExternalMark());
				
				if(inDiff >= exDiff)
				{	
					finalMark = (tmark.getExternalMark()+tmark.getThirdExaminerMark())/(2.0);
				}
				else 
				{
					finalMark = (tmark.getInternalMark()+tmark.getThirdExaminerMark())/(2.0);
				}
				
				
			}
		}
		
		if(finalMark==null) {finalMark = 0.0; return;}
		
		finalMark = UtilityService.round(finalMark, 2);

		tmark.setFinalMark(finalMark);
		
		Double totalMark = 0.0;
		
		if(tmark.getTutorialMark() !=null && tmark.getFinalMark() != null)
		{
			totalMark = tmark.getTutorialMark() + tmark.getFinalMark();
			tmark.setTotalMark(totalMark);			
		}
		
		//gp //letter
		TStudent tstudent = tmark.getTstudent();
		TCourse tcourse = tstudent.getTcourse();
		
		Double allocatedTotalMark = (double) (tcourse.getTotalFinalMark()+tcourse.getTotalTutorialMark());
		
		BigDecimal outtaHundred = BigDecimal.ONE;
		
		if(allocatedTotalMark!=100.0)
		{
			BigDecimal totalMarkBd = new BigDecimal(totalMark);
			BigDecimal allocatedTotalMarkBd = new BigDecimal(allocatedTotalMark);
			
			outtaHundred = totalMarkBd.multiply(new BigDecimal(100.0));
			
			outtaHundred = outtaHundred.divide(allocatedTotalMarkBd, 2, RoundingMode.HALF_UP);

		}
		else outtaHundred = new BigDecimal(totalMark);
		
		Double gradePoint = 0.0;
		String letterGrade = "";
		String remarks="PASSED";
		
		if(outtaHundred.compareTo(new BigDecimal(80))>=0) {gradePoint = 4.0; letterGrade="A+";}
		else if(outtaHundred.compareTo(new BigDecimal(75))>=0) {gradePoint = 3.75; letterGrade="A";}
		else if(outtaHundred.compareTo(new BigDecimal(70))>=0) {gradePoint = 3.5;  letterGrade="A-";}
		else if(outtaHundred.compareTo(new BigDecimal(65))>=0) {gradePoint = 3.25; letterGrade="B+";}
		else if(outtaHundred.compareTo(new BigDecimal(60))>=0) {gradePoint = 3.0;  letterGrade="B";}
		else if(outtaHundred.compareTo(new BigDecimal(55))>=0) {gradePoint = 2.75; letterGrade="B-";}
		else if(outtaHundred.compareTo(new BigDecimal(50))>=0) {gradePoint = 2.5; letterGrade="C+";}
		else if(outtaHundred.compareTo(new BigDecimal(45))>=0) {gradePoint = 2.25; letterGrade="C";}
		else if(outtaHundred.compareTo(new BigDecimal(40))>=0) {gradePoint = 2.0; letterGrade="D";}
		else {gradePoint = 0.0; letterGrade="F"; remarks = "F-" + tstudent.getTcourse().getTcourseCode(); }
		
		tmark.setGradePoint(gradePoint);
		tmark.setLetterGrade(letterGrade);
		tmark.setRemarks(remarks);
	}


}
