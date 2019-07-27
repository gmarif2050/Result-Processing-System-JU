package com.rps.entities.tabulation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class TStudent implements Comparable<TStudent>{

	@Id
	@Column(name="tstudent_id_pk", nullable = false, updatable = false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long tstudentId;
	
	private Long examRoll;
	
	private Long regNumber;
	
	private Long classRoll;

	@ManyToOne
	@JoinColumn(name = "tcourse_id_fk")
	private TCourse tcourse;
	
	@OneToOne(mappedBy="tstudent")
	//@OnDelete(action = OnDeleteAction.CASCADE)
	private TMark tmark;
	
	public TStudent() {}

	public TStudent(Long examRoll, Long regNumber, Long classRoll, TCourse tcourse, TMark tmarks) {
		super();
		this.examRoll = examRoll;
		this.regNumber = regNumber;
		this.classRoll = classRoll;
		this.tcourse = tcourse;
		this.tmark = tmark;
	}
	
	

	public Long getTstudentId() {
		return tstudentId;
	}

	public void setTstudentId(Long tstudentId) {
		this.tstudentId = tstudentId;
	}

	public Long getExamRoll() {
		return examRoll;
	}

	public void setExamRoll(Long examRoll) {
		this.examRoll = examRoll;
	}

	public Long getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(Long regNumber) {
		this.regNumber = regNumber;
	}

	public Long getClassRoll() {
		return classRoll;
	}

	public void setClassRoll(Long classRoll) {
		this.classRoll = classRoll;
	}

	public TCourse getTcourse() {
		return tcourse;
	}

	public void setTcourse(TCourse tcourse) {
		this.tcourse = tcourse;
	}

	public TMark getTmark() {
		return tmark;
	}

	public void setTmark(TMark tmark) {
		this.tmark = tmark;
	}

	@Override
	public int compareTo(TStudent o) {
		return (int) (this.getExamRoll()-o.getExamRoll());
	}


	
	
}
