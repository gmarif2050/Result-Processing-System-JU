package com.rps.service.tabulation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rps.entities.tabulation.Teacher;

@Service
public class TeacherDetailsServiceImp implements UserDetailsService{

	private static final Logger LOG = LoggerFactory.getLogger(TeacherDetailsServiceImp.class);
	
	@Autowired
	TeacherServiceImpl teacherServiceImpl;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Teacher teacher = teacherServiceImpl.getTeacherByEmail(email);
		
		if(teacher==null)
		{
			LOG.warn("Email {} not found!", email);
			throw new UsernameNotFoundException("Email " + email + " not found!");
		}
		return teacher;
	}

}
