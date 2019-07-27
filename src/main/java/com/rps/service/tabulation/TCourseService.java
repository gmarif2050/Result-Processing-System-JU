package com.rps.service.tabulation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rps.entities.storage.Student;
import com.rps.entities.tabulation.TCourse;

@Service
public interface TCourseService {
	
	void addCourse(TCourse tcourse);
	
	TCourse getTCourse(Long courseId);

	void removeCourse(TCourse tcourse);
}
