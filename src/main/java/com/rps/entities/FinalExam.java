package com.rps.entities;

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
	private String examName;
	private BigDecimal GPA;

	
	@OneToMany(mappedBy="finalExam", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<MarkDistribution> markDistribution = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name = "degree_id_fk")
	private Degree degree;
	
	public FinalExam() {}
	
	public FinalExam(String examName, BigDecimal gPA, Degree degree) {
		super();
		this.examName = examName;
		GPA = gPA;
		this.degree = degree;
	}

	public Long getExamId() {
		return examId;
	}

	public void setExamId(Long examId) {
		this.examId = examId;
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

	public Set<MarkDistribution> getMarkDistribution() {
		return markDistribution;
	}

	public void setMarkDistribution(Set<MarkDistribution> markDistribution) {
		this.markDistribution = markDistribution;
	}


	public Degree getDegree() {
		return degree;
	}

	public void setDegree(Degree degree) {
		this.degree = degree;
	}

	@Override
	public String toString() {
		return "FinalExam [examId=" + examId + ", examName=" + examName + ", GPA=" + GPA + ", markDistribution="
				+ markDistribution + ", degreePursuing=" + "]";
	}
	
	
	
}
