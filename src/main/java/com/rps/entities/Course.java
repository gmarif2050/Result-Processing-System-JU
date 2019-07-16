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
public class Course {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="course_id_pk", nullable = false, updatable = false)
	private Long courseId;
	private String courseCode;
	private String courseName;
	private BigDecimal noOfCredit;
	private BigDecimal tutorialMark;
	private BigDecimal finalExamMark;
	private BigDecimal markAchievedby100;
	private BigDecimal gradePoint;
	
	@ManyToOne
	@JoinColumn(name = "exam_id_fk")
	private FinalExam finalExam;
	
	public Course() {}
	
	public Course(String courseCode, String courseName, BigDecimal noOfCredit,
			BigDecimal tutorialMark, BigDecimal finalExamMark, BigDecimal markAchievedby100, BigDecimal gradePoint,
			FinalExam finalExam) {
		super();
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.noOfCredit = noOfCredit;
		this.tutorialMark = tutorialMark;
		this.finalExamMark = finalExamMark;
		this.markAchievedby100 = markAchievedby100;
		this.gradePoint = gradePoint;
		this.finalExam = finalExam;
	}

	public BigDecimal getTutorialMark() {
		return tutorialMark;
	}

	public void setTutorialMark(BigDecimal tutorialMark) {
		this.tutorialMark = tutorialMark;
	}


	public BigDecimal getFinalExamMark() {
		return finalExamMark;
	}

	public void setFinalExamMark(BigDecimal finalExamMark) {
		this.finalExamMark = finalExamMark;
	}


	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public BigDecimal getNoOfCredit() {
		return noOfCredit;
	}

	public void setNoOfCredit(BigDecimal noOfCredit) {
		this.noOfCredit = noOfCredit;
	}

	public BigDecimal getMarkAchievedby100() {
		return markAchievedby100;
	}

	public void setMarkAchievedby100(BigDecimal markAchievedby100) {
		this.markAchievedby100 = markAchievedby100;
	}

	public BigDecimal getGradePoint() {
		return gradePoint;
	}

	public void setGradePoint(BigDecimal gradePoint) {
		this.gradePoint = gradePoint;
	}

	public FinalExam getFinalExam() {
		return finalExam;
	}

	public void setFinalExam(FinalExam finalExam) {
		this.finalExam = finalExam;
	}

	@Override
	public String toString() {
		return "MarkDistribution [courseId=" + courseId + ", courseCode=" + courseCode + ", courseName=" + courseName
				+ ", noOfCredit=" + noOfCredit + ", markAchievedby100=" + markAchievedby100 + ", gradePoint="
				+ gradePoint + ", finalExam=" + finalExam + "]";
	}

	
	
	
}
