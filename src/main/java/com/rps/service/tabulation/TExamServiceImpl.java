package com.rps.service.tabulation;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rps.dao.tabulation.TExamDao;
import com.rps.entities.tabulation.TCourse;
import com.rps.entities.tabulation.TExam;

@Transactional
@Service
public class TExamServiceImpl implements TExamService {

	@Autowired
	TExamDao texamDao;
	@Autowired
	TCourseService tcourseService;
	
	@Override
	public void addExam(TExam texam) {
		texamDao.save(texam);
	}

	@Override
	public void updateExamInfo(TExam texam) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Set<TCourse> findCourses(long texamId)
	{
		TExam texam = texamDao.findByTexamId(texamId);
		return texam.getTcourses();
	}

	@Override
	public TExam getExam(long examId) {
		return texamDao.findByTexamId(examId);
	}

	@Override
	public void removeTExam(TExam texam) {
		
		Set<TCourse> tcourseSet = texam.getTcourses();

		for(TCourse tcourse: tcourseSet)
		{
			tcourseService.removeCourse(tcourse);			
		}
		
		texamDao.delete(texam);
		
	}

}
