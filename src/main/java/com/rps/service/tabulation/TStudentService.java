package com.rps.service.tabulation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rps.entities.storage.Student;
import com.rps.entities.tabulation.TCourse;
import com.rps.entities.tabulation.TStudent;

@Service
public interface TStudentService {

	void addTStudent(TStudent tstudent);
	
	TStudent getTStudent(Long tstudentId);
	
	void registerDefaultStudents(List<Student> studentList, TCourse tcourse);
	
	void deleteStudentReg(Long tstudentId);

	void bulkStudentRegister(List<String> examRollList, TCourse tcourse);

	void bulkStudentDeRegister(List<String> examRollList, TCourse tcourse);

	List<TStudent> getTStudentByTCourse(TCourse tcourse);

	void removeTStudent(TStudent tstudent);

	List<TStudent> getTStudentByExamRoll(Long examRoll);
	
	TStudent getTStudentByExamRollAndTcourse(Long examRoll, TCourse tcourse);
	
	
}
