package com.rps.service.storage;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rps.entities.storage.Program;
import com.rps.entities.storage.Student;

@Service
public interface ProgramService {
	List<Program> getProgramByStudentAndProgramCode(Student student, String programCode);

	void addProgram(Program programObj);
}
