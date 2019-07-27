package com.rps.service.tabulation;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rps.entities.tabulation.TCourse;
import com.rps.entities.tabulation.TExam;

@Service
public interface TExamService {
	
	void addExam(TExam texam);
	
	void updateExamInfo(TExam texam);

	TExam getExam(long examId);

	Set<TCourse> findCourses(long texamId);

	void removeTExam(TExam texam);
	
}
