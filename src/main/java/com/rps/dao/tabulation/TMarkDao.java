package com.rps.dao.tabulation;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rps.entities.tabulation.TMark;
import com.rps.entities.tabulation.TStudent;

public interface TMarkDao extends CrudRepository<TMark, Long>{
	
	TMark findByTmarkId(Long tmarkId);
	
	TMark findByTstudent(TStudent tstudent);
}
