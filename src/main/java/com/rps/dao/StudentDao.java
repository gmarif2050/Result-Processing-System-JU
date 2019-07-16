package com.rps.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rps.entities.Student;

public interface StudentDao extends CrudRepository<Student, Long>{
	Student findByRegNumber(String regNumber);
	Student findByExamRoll(String examRoll);
	Student findBySession(String session);
	List<Student> findByDeptAndSession(String dept, String session);
	List<Student> findByExamRollOrRegNumber(String examRoll, String regNumber);
}