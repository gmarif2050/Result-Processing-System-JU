package com.rps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ResultProcessingSystemJuApplication {

	
	/*
	 * 
	 * tabulator 
	 * 
	 * exam info - 2014-15//CSE//8//4th Year 2nd Semester 2018
	 * 
	 * courses under this exam info --- where to get them from?? 
	 * 
	 * tabulator is very keen to do this data entry of exam info! okay done u do it :/
	 * 
	 * register students under this courses! different courses can have different students! 
	 * some dropped students miss registration under the exam, some repeater gets add into the 
	 * valid registered student list!
	 * 
	 * where to get the students from?? a general valid batch student list needs to be imported! 
	 * some gets unselected from there, xtra added the repeating students!
	 * 
	 * for each course, there can be different students list available for the tabulator, 
	 * i need to keep track of that for every single course!
	 * 
	 * according to that list, tuto,internal,external,thirdexaminer marks will be added!
	 * 
	 * then, result sheet i.e. tablulation form for each individual student will be created
	 * to view , tabulator can view that!
	 * 
	 * 
	 * 
	 * 
	 * dept ->12m-> program ->12m->
	 * 
	 *  
	 *  teacher -> exam 12m-> courses ->12m-> students ->12m-> marks
	 *  
	 *  
	 *  
	 *  tabulator teacher logs into the system...
	 *  
	 *  adds exam...
	 *  teacher(tid,name,designation,dept,email,password)
	 *  
	 *  
	 *  FianlExamToBeTabulated(examID, examName, session, teacher)
	 *      
	 *  
	 *  courses info entry(courseNumber, name, credit, tutomark, finalmark, type)
	 *  
	 *  course register --- improt studnts automatically into the courses - 
	 *  students(oi session and dept er under e joto student register kora ase shb gular 
	 *  roll ei final exam er under e chole ashbe)
	 *  --- just role ashlei hobe karn info already joma ase
	 *  add new examroll -- repeater der jnne... just exam roll add dilei hobe baki ta 
	 *  student info db theke dekhe neya jabe
	 *  
	 *  student remove o kora jabe.. dropped student jara 
	 *  
	 *  
	 *  then.... 
	 *  each student er jnne tuto mark entry r sujog thakbe... 
	 *  internal external mark..
	 *  
	 *  teacher 2 exam 2 course 2 student(import,add,remove) 2 mark(tuto,internal,external...) 
	 *  
	 *  
	 *  
	 *  
	 *  
	 */
	
	public static void main(String[] args) {
		SpringApplication.run(ResultProcessingSystemJuApplication.class, args);
	}

}
