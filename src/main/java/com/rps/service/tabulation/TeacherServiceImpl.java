package com.rps.service.tabulation;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rps.dao.tabulation.TeacherDao;
import com.rps.entities.tabulation.TExam;
import com.rps.entities.tabulation.Teacher;

@Transactional
@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	TeacherDao teacherDao;
	
	@Override
	public void addTeacher(Teacher teacher) {
		teacherDao.save(teacher);
	}
	
	@Override
	public Teacher getTeacher(long teacherId)
	{
		return teacherDao.findByTeacherId(teacherId);
	}

	@Override
	public long authenticateTeacher(Teacher postedteacher) {
		
		long teacherId = -1;
		
		Teacher teacher = teacherDao.findByEmail(postedteacher.getEmail());
		
		if(teacher != null && teacher.getPassword().equals(postedteacher.getPassword()))
		{
			teacherId = teacher.getTeacherId();
			return teacherId;
		}
		else 
		{
			return teacherId;
		}
	}

	@Override
	public Set<TExam> findExams(long teacherId) {
		Teacher teacher = teacherDao.findByTeacherId(teacherId);
		return teacher.getTexams();
	}

	@Override
	public Teacher getTeacherByEmail(String email) {
		return teacherDao.findByEmail(email);
	}

}
