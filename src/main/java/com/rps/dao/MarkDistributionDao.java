package com.rps.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rps.entities.FinalExam;
import com.rps.entities.MarkDistribution;

public interface MarkDistributionDao extends CrudRepository<MarkDistribution, Long>{

	List<MarkDistribution> findByFinalExam(FinalExam finalExam);
	
}
