package com.rps.service.tabulation;

import org.springframework.stereotype.Service;

import com.rps.entities.tabulation.TMark;
import com.rps.entities.tabulation.TStudent;

@Service
public interface TMarkService {
	
	TMark getTMarkByTStudent(TStudent tstudent);
	
	void addMarks(TMark tmark);

	void removeTMark(TMark tmark);
	
	void updateAllMarks(TMark tmark);
}
