package com.rps.service.tabulation;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rps.entities.tabulation.TExam;
import com.rps.entities.tabulation.Teacher;

@Service
public interface TeacherService {
	
	void addTeacher(Teacher teacher);

	long authenticateTeacher(Teacher teacher);

	Set<TExam> findExams(long teacherId);

	Teacher getTeacher(long teacherId);

	Teacher getTeacherByEmail(String email);
	
}
