package com.rps.dao.tabulation;

import org.springframework.data.repository.CrudRepository;

import com.rps.entities.tabulation.TCourse;

public interface TCourseDao  extends CrudRepository<TCourse, Long>{
	
	TCourse findByTcourseId(Long tcourseId);
	
}
