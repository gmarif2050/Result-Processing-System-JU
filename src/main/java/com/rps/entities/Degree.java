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


@Entity
public class Degree{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="degree_id_pk", nullable = false, updatable = false)
	private Long degreeId;
	private String degreeName;
	private Double cumulativeGPAtillNow;
	
	@OneToMany(mappedBy="degree", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<FinalExam> finalExams = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name = "student_id_fk")
	private Student student;
	
	public Degree()
	{
		
	}

	public Degree(String degreeName, Double cumulativeGPAtillNow, Student student) {
		super();
		this.degreeName = degreeName;
		this.cumulativeGPAtillNow = cumulativeGPAtillNow;
		this.student = student;
	}

	public Long getDegreeId() {
		return degreeId;
	}

	public void setDegreeId(Long degreeId) {
		this.degreeId = degreeId;
	}

	public String getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
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

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Override
	public String toString() {
		return "Degree [degreeId=" + degreeId + ", degreeName=" + degreeName + ", cumulativeGPAtillNow="
				+ cumulativeGPAtillNow + ", finalExams=" + finalExams + ", student=" + student + "]";
	}

	
	
}
