package com.rps.entities.storage;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Student implements Comparable<Student>{
	
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	@Column(name="exam_roll_pk", nullable = false, updatable = false)
	private Long examRoll;
	private Long regNumber;
	private Long classRoll;
	private String name;
	private String session;
	private String dept;
	private String hall;
	private String phone;
	private String email;
	private String fathersName;
	private String mothersName;
	
	@OneToMany(mappedBy="student", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<Program> programs = new HashSet<>();
	
	@Override
	public int compareTo(Student student) {		
		return (int) (this.getRegNumber() - student.getRegNumber());
	}

	public Long getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(Long regNumber) {
		this.regNumber = regNumber;
	}

	public Long getExamRoll() {
		return examRoll;
	}

	public void setExamRoll(Long examRoll) {
		this.examRoll = examRoll;
	}

	public Long getClassRoll() {
		return classRoll;
	}

	public void setClassRoll(Long classRoll) {
		this.classRoll = classRoll;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getHall() {
		return hall;
	}

	public void setHall(String hall) {
		this.hall = hall;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFathersName() {
		return fathersName;
	}

	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}

	public String getMothersName() {
		return mothersName;
	}

	public void setMothersName(String mothersName) {
		this.mothersName = mothersName;
	}

	public Set<Program> getPrograms() {
		return programs;
	}

	public void setPrograms(Set<Program> programs) {
		this.programs = programs;
	}
	
	

}
