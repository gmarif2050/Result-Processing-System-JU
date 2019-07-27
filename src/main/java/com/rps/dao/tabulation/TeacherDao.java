package com.rps.dao.tabulation;

import org.springframework.data.repository.CrudRepository;

import com.rps.entities.tabulation.Teacher;

public interface TeacherDao extends CrudRepository<Teacher, Long>{

	Teacher findByTeacherId(long teacherId);
	Teacher findByEmail(String email);
}
