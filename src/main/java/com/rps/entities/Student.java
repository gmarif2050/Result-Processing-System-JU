package com.rps.entities;

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
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="student_id_pk", nullable = false, updatable = false)
	private Long studentId;
	private String examRoll;
	private String regNumber;
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
	
	
	public Long getStudentId() {
		return studentId;
	}


	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}


	public String getExamRoll() {
		return examRoll;
	}


	public void setExamRoll(String examRoll) {
		this.examRoll = examRoll;
	}


	public String getRegNumber() {
		return regNumber;
	}


	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
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


	@Override
	public int compareTo(Student student) {		
		return this.getRegNumber().compareTo(student.getRegNumber());
	}

	
	
	
}
