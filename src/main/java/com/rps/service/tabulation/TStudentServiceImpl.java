package com.rps.service.tabulation;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rps.dao.tabulation.TStudentDao;
import com.rps.entities.storage.Student;
import com.rps.entities.tabulation.TCourse;
import com.rps.entities.tabulation.TMark;
import com.rps.entities.tabulation.TStudent;
import com.rps.service.storage.StudentService;

@Service
@Transactional
public class TStudentServiceImpl implements TStudentService {
	
	@Autowired
	TStudentDao tstudentDao;
	@Autowired
	TStudentService tstudentService;
	@Autowired
	StudentService studentService;
	@Autowired
	TMarkService tmarkService;
	
	@Override
	public void registerDefaultStudents(List<Student> studentList, TCourse tcourse) {
		
		for(Student student: studentList)
		{			
			TStudent tstudentTmp = tstudentDao.findByExamRollAndTcourse(student.getExamRoll(), tcourse);
			
			if(tstudentTmp!=null) continue;
			
			TStudent tstudent = new TStudent();
			tstudent.setExamRoll(student.getExamRoll());
			tstudent.setRegNumber(student.getRegNumber());
			tstudent.setClassRoll(student.getClassRoll());
			tstudent.setTcourse(tcourse);
			
			tstudentDao.save(tstudent);
		}
	}

	@Override
	@Transactional
	public void deleteStudentReg(Long studentId) {
		
		TStudent tstudent = tstudentDao.findByTstudentId(studentId);
		tstudentService.removeTStudent(tstudent);
	}

	@Override
	public void bulkStudentRegister(List<String> examRollList, TCourse tcourse) {
		
		
		for(String examRollStr: examRollList)
		{
			Long examRoll = Long.valueOf(examRollStr);
			
			Student student =  studentService.getStudentByExamRoll(examRoll);			
			if(student==null) continue;
			
			TStudent tstudentTmp = tstudentDao.findByExamRollAndTcourse(student.getExamRoll(), tcourse);
			
			if(tstudentTmp!=null) continue;	
			
			TStudent tstudent = new TStudent();
			tstudent.setExamRoll(student.getExamRoll());
			tstudent.setRegNumber(student.getRegNumber());
			tstudent.setClassRoll(student.getClassRoll());
			tstudent.setTcourse(tcourse);
			
			tstudentDao.save(tstudent);
		}
	}


	@Override
	public TStudent getTStudent(Long tstudentId) {
		return tstudentDao.findByTstudentId(tstudentId);
	}

	@Override
	public void addTStudent(TStudent tstudent) {
		tstudentDao.save(tstudent);
	}

	@Override
	public List<TStudent> getTStudentByTCourse(TCourse tcourse) {
		return tstudentDao.findByTcourse(tcourse);
	}

	@Override
	public void removeTStudent(TStudent tstudent) {
		
		TMark tmark = tmarkService.getTMarkByTStudent(tstudent);
		if(tmark!=null)
			tmarkService.removeTMark(tmark);	
		else if(tstudent!=null)
			tstudentDao.delete(tstudent);
		//tmark is the owner of onetoonerelationship, so removing tmark 
		// automagically removes tstudent!
	}
	
	@Override
	public void bulkStudentDeRegister(List<String> examRollList, TCourse tcourse) {
				
		for(String examRollStr: examRollList)
		{
			Long examRoll = Long.valueOf(examRollStr);
			
			TStudent tstudent = tstudentDao.findByExamRollAndTcourse(examRoll, tcourse);
			
			if(tstudent==null) continue;
			
			removeTStudent(tstudent);
		}
		
	}

	@Override
	public List<TStudent> getTStudentByExamRoll(Long examRoll) {
		return tstudentDao.findByExamRoll(examRoll);
	}

	@Override
	public TStudent getTStudentByExamRollAndTcourse(Long examRoll, TCourse tcourse) {
		return tstudentDao.findByExamRollAndTcourse(examRoll, tcourse);
	}
	
	

	

}
