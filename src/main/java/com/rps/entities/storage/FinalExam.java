package com.rps.entities.storage;

import java.math.BigDecimal;
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

@Entity
public class FinalExam {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="exam_id_pk", nullable = false, updatable = false)
	private Long examId;
	private Long examNumber;
	private String examName;
	private BigDecimal GPA;
	
	@OneToMany(mappedBy="finalExam", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<Course> courses = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name = "program_id_fk")
	private Program program;
	
	public FinalExam() {}
	
	public FinalExam(Long examNumber, String examName, BigDecimal gPA, Program program) {
		super();
		this.examNumber = examNumber;
		this.examName = examName;
		GPA = gPA;
		this.program = program;
	}

	public Long getExamId() {
		return examId;
	}

	public void setExamId(Long examId) {
		this.examId = examId;
	}

	public Long getExamNumber() {
		return examNumber;
	}

	public void setExamNumber(Long examNumber) {
		this.examNumber = examNumber;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public BigDecimal getGPA() {
		return GPA;
	}

	public void setGPA(BigDecimal gPA) {
		GPA = gPA;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourse(Set<Course> courses) {
		this.courses = courses;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	
}
