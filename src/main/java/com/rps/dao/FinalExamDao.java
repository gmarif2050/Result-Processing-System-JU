package com.rps.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.rps.entities.Program;
import com.rps.entities.FinalExam;

public interface FinalExamDao extends CrudRepository<FinalExam, Long>{

	List<FinalExam> findByProgram(Program program);

	FinalExam findByExamId(Long finalExamId);
}