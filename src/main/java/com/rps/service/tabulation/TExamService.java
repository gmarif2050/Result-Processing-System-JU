package com.rps.service.tabulation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rps.entities.tabulation.TCourse;
import com.rps.entities.tabulation.TExam;
import com.rps.entities.tabulation.TStudent;

@Service
public interface TExamService {
	
	void addExam(TExam texam);
	
	void updateExamInfo(TExam texam);

	TExam getExam(long examId);

	Set<TCourse> findCourses(long texamId);

	void removeTExam(TExam texam);
	
	ByteArrayInputStream generatePdf(TExam texam, TCourse tcourse, List<TStudent> tstudentList) throws MalformedURLException, IOException;
	
}
