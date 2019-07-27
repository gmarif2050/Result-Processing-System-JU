package com.rps.dao.tabulation;

import org.springframework.data.repository.CrudRepository;

import com.rps.entities.tabulation.TExam;

public interface TExamDao extends CrudRepository<TExam, Long>{
	TExam findByTexamId(Long texamId);
	
}
