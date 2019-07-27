package com.rps.service.storage;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rps.dao.storage.ProgramDao;
import com.rps.entities.storage.Program;
import com.rps.entities.storage.Student;

@Transactional
@Service
public class ProgramServiceImpl implements ProgramService {

	@Autowired
	ProgramDao programDao;
	
	@Override
	public List<Program> getProgramByStudentAndProgramCode(Student student, String programCode) {
		return programDao.findByStudentAndProgramCode(student, programCode);
	}

	@Override
	public void addProgram(Program programObj) {
		programDao.save(programObj);
	}

}
