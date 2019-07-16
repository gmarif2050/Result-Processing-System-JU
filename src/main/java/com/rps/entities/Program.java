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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
public class Program{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="program_id_pk", nullable = false, updatable = false)
	private Long programId;
	private String programCode;
	private Double cumulativeGPAtillNow;
	
	@OneToMany(mappedBy="program", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<FinalExam> finalExams = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name = "student_id_fk")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Student student;
	
	public Program()
	{
		
	}

	public Program(String programCode, Double cumulativeGPAtillNow, Student student) {
		super();
		this.programCode = programCode;
		this.cumulativeGPAtillNow = cumulativeGPAtillNow;
		this.student = student;
	}

	public Long getProgramId() {
		return programId;
	}

	public void setProgramId(Long programId) {
		this.programId = programId;
	}

	public String getProgramCode() {
		return programCode;
	}

	public void setaProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public Double getCumulativeGPAtillNow() {
		return cumulativeGPAtillNow;
	}

	public void setCumulativeGPAtillNow(Double cumulativeGPAtillNow) {
		this.cumulativeGPAtillNow = cumulativeGPAtillNow;
	}

	

	public Set<FinalExam> getFinalExams() {
		return finalExams;
	}

	public void setFinalExams(Set<FinalExam> finalExams) {
		this.finalExams = finalExams;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	
	
}
