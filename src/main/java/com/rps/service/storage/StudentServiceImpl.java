package com.rps.service.storage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rps.dao.storage.StudentDao;
import com.rps.entities.storage.Student;

@Transactional
@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentDao studentDao;
	
	@Override
	public void addStudent(Student student) {
		studentDao.save(student);
	}

	@Override
	public List<Student> findStudents(String dept, String session) {
		return studentDao.findByDeptAndSession(dept, session);
	}

	@Override
	public Student getStudentByExamRoll(Long examRoll) {
		return studentDao.findByExamRoll(examRoll);
	}
	
	

}
