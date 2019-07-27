package com.rps.dao.storage;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rps.entities.storage.Student;

public interface StudentDao extends CrudRepository<Student, Long>{
	Student findByRegNumber(Long regNumber);
	Student findByExamRoll(Long examRoll);
	Student findBySession(String session);
	List<Student> findByDeptAndSession(String dept, String session);
	List<Student> findByExamRollOrRegNumber(Long examRoll, Long regNumber);
}