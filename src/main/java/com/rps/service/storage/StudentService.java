package com.rps.service.storage;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rps.entities.storage.Student;

@Service
public interface StudentService {

	void addStudent(Student student);

	List<Student> findStudents(String deptName, String session);
	
	Student getStudentByExamRoll(Long examRoll);
}
