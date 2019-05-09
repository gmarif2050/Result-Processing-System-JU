package com.rps.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rps.entities.Degree;
import com.rps.entities.Student;


public interface DegreeDao  extends CrudRepository<Degree, Long>{
	
	Degree findByDegreeName(String str);
	Degree findByDegreeId(Long id);
	List<Degree> findByStudent(Student stu);
	List<Degree> findByStudentAndDegreeName(Student student, String degreeName);
}
