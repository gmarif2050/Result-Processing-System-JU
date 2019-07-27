package com.rps.service.tabulation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rps.dao.tabulation.TCourseDao;
import com.rps.entities.tabulation.TCourse;
import com.rps.entities.tabulation.TStudent;

@Transactional
@Service
public class TCourseServiceImpl implements TCourseService {

	@Autowired
	TCourseDao tcourseDao;
	@Autowired
	TStudentService tstudentService;
	
	@Override
	public void addCourse(TCourse tcourse) {
		tcourseDao.save(tcourse);
	}

	@Override
	public TCourse getTCourse(Long tcourseId) {
		return tcourseDao.findByTcourseId(tcourseId);
	}

	@Override
	public void removeCourse(TCourse tcourse) {
		
		List <TStudent> tstudentList = tstudentService.getTStudentByTCourse(tcourse);
		
		for(TStudent tstudent: tstudentList)
		{
			tstudentService.removeTStudent(tstudent);
		}
		tcourseDao.delete(tcourse);
	}

	

	
	
	
	
}
