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
	
	@OneToMany(mappedBy="student", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<Degree> degrees = new HashSet<>();

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

	public Set<Degree> getDegrees() {
		return degrees;
	}

	public void setDegrees(Set<Degree> degrees) {
		this.degrees = degrees;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", examRoll=" + examRoll + ", regNumber=" + regNumber + ", name="
				+ name + ", session=" + session + ", dept=" + dept + ", hall=" + hall + ", phone=" + phone + ", email="
				+ email + ", degrees=" + degrees + "]";
	}

	@Override
	public int compareTo(Student student) {		
		return this.getRegNumber().compareTo(student.getRegNumber());
	}

	
	
	
}
