package com.rps.entities.tabulation;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class TMark {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="tmark_id_pk", nullable = false, updatable = false)
	private Long tmarkId;
	
	private Double tutorialMark;
	private Double internalMark;
	private Double externalMark;
	private Double thirdExaminerMark;
	private Double finalMark;
	private Double totalMark;
	private Double gradePoint;
	private String letterGrade;
	private String remarks;
	
	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY , orphanRemoval=true)
    @JoinColumn(name = "tstudent_id_fk", referencedColumnName = "tstudent_id_pk")
	private TStudent tstudent;

	public TMark()
	{
		super();
		this.tutorialMark = null;
		this.internalMark = null;
		this.externalMark = null;
		this.thirdExaminerMark = null;
		this.totalMark = 0.0;
		this.finalMark = 0.0;
		this.gradePoint = 0.0;
		this.letterGrade = "N/A";
		this.remarks = "N/A";
	}
	

	public TMark(Long tmarkId, Double tutorialMark, Double internalMark, Double externalMark, Double thirdExaminerMark,
			Double finalMark, Double totalMark, Double gradePoint, String letterGrade, String remarks,
			TStudent tstudent) {
		super();
		this.tmarkId = tmarkId;
		this.tutorialMark = tutorialMark;
		this.internalMark = internalMark;
		this.externalMark = externalMark;
		this.thirdExaminerMark = thirdExaminerMark;
		this.finalMark = finalMark;
		this.totalMark = totalMark;
		this.gradePoint = gradePoint;
		this.letterGrade = letterGrade;
		this.remarks = remarks;
		this.tstudent = tstudent;
	}


	public Long getTmarkId() {
		return tmarkId;
	}

	public void setTmarkId(Long tmarkId) {
		this.tmarkId = tmarkId;
	}

	public Double getTutorialMark() {
		return tutorialMark;
	}

	public void setTutorialMark(Double tutorialMark) {
		this.tutorialMark = tutorialMark;
	}

	public Double getInternalMark() {
		return internalMark;
	}

	public void setInternalMark(Double internalMark) {
		this.internalMark = internalMark;
	}

	public Double getExternalMark() {
		return externalMark;
	}

	public void setExternalMark(Double externalMark) {
		this.externalMark = externalMark;
	}

	public Double getThirdExaminerMark() {
		return thirdExaminerMark;
	}

	public void setThirdExaminerMark(Double thirdExaminerMark) {
		this.thirdExaminerMark = thirdExaminerMark;
	}

	public Double getTotalMark() {
		return totalMark;
	}

	public void setTotalMark(Double totalMark) {
		this.totalMark = totalMark;
	}

	public Double getGradePoint() {
		return gradePoint;
	}

	public void setGradePoint(Double gradePoint) {
		this.gradePoint = gradePoint;
	}

	public String getLetterGrade() {
		return letterGrade;
	}

	public void setLetterGrade(String letterGrade) {
		this.letterGrade = letterGrade;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public TStudent getTstudent() {
		return tstudent;
	}

	public void setTstudent(TStudent tstudent) {
		this.tstudent = tstudent;
	}


	public Double getFinalMark() {
		return finalMark;
	}


	public void setFinalMark(Double finalMark) {
		this.finalMark = finalMark;
	}
	
	
}
