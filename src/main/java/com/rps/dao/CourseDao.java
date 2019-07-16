package com.rps.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rps.entities.FinalExam;
import com.rps.entities.Course;

public interface CourseDao extends CrudRepository<Course, Long>{

	List<Course> findByFinalExam(FinalExam finalExam);
	Course findByCourseId(Long courseId);
	
}
