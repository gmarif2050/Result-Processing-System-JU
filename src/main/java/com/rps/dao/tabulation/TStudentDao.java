package com.rps.dao.tabulation;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rps.entities.tabulation.TCourse;
import com.rps.entities.tabulation.TStudent;

public interface TStudentDao  extends CrudRepository<TStudent, Long>{
	
	List<TStudent> findByExamRoll(Long examRoll);

	TStudent findByTstudentId(Long studentId);

	TStudent findByExamRollAndTcourse(Long examRoll, TCourse tcourse);

	List<TStudent> findByTcourse(TCourse tcourse);
}
