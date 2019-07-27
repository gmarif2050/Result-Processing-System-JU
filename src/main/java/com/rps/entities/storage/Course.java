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
public class Course {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="course_id_pk", nullable = false, updatable = false)
	private Long courseId;
	
	private Long courseCode;
	private String courseName;
	private double courseCredit;
	private long totalTutorialMark;
	private long totalFinalMark;
	private String courseType;
	
	private Double tutorialMark;
	private Double internalMark;
	private Double externalMark;
	private Double thirdExaminerMark;
	private Double finalMark;
	private Double totalMark;
	private Double gradePoint;
	private String letterGrade;
	private String remarks;
	
	@ManyToOne
	@JoinColumn(name = "exam_id_fk")
	private FinalExam finalExam;
	
	
}
