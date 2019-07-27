package com.rps.dto.tabulation;

public class MarkEntity implements Comparable<MarkEntity>{

	private Long courseNumber;
	private String courseName;
	private Double courseCredit;
	
	private Double tutorialMark;
	private Double internalMark;
	private Double externalMark;
	private Double thirdExaminerMark;
	private Double finalMark;
	private Double totalMark;
	private Double gradePoint;
	private String letterGrade;
	private String remarks;
	
	public Long getCourseNumber() {
		return courseNumber;
	}
	public void setCourseNumber(Long courseNumber) {
		this.courseNumber = courseNumber;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
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
	public Double getFinalMark() {
		return finalMark;
	}
	public void setFinalMark(Double finalMark) {
		this.finalMark = finalMark;
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
	
	public Double getCourseCredit() {
		return courseCredit;
	}
	public void setCourseCredit(Double courseCredit) {
		this.courseCredit = courseCredit;
	}
	@Override
	public int compareTo(MarkEntity o) {
		return this.getCourseNumber().compareTo(o.getCourseNumber());
	}
	
}
