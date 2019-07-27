package com.rps.entities.tabulation;

import java.util.HashSet;
import java.util.Set;

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
public class TCourse{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="tcourse_id_pk", nullable = false, updatable = false)
	private Long tcourseId;

	private Long tcourseCode;
	private String tcourseName;
	private Double tcourseCredit;
	private Long totalTutorialMark;
	private Long totalFinalMark;
	private String tcourseType;
	
	@ManyToOne
	@JoinColumn(name = "texam_id_fk")
	private TExam texam;
	
	@OneToMany(mappedBy="tcourse", fetch=FetchType.LAZY)
//	@Cascade({CascadeType.ALL})
	private Set<TStudent> tstudents = new HashSet<>();

	
	public TCourse() {}
	
	public TCourse(Long tcourseCode, String tcourseName, Double tcourseCredit, Long totalTutorialMark,
			Long totalFinalMark, String tcourseType, TExam texam, Set<TStudent> tstudents) {
		super();
		this.tcourseCode = tcourseCode;
		this.tcourseName = tcourseName;
		this.tcourseCredit = tcourseCredit;
		this.totalTutorialMark = totalTutorialMark;
		this.totalFinalMark = totalFinalMark;
		this.tcourseType = tcourseType;
		this.texam = texam;
		this.tstudents = tstudents;
	}

	public Long getTcourseId() {
		return tcourseId;
	}

	public void setTcourseId(Long tcourseId) {
		this.tcourseId = tcourseId;
	}

	public Long getTcourseCode() {
		return tcourseCode;
	}

	public void setTcourseCode(Long tcourseCode) {
		this.tcourseCode = tcourseCode;
	}

	public String getTcourseName() {
		return tcourseName;
	}

	public void setTcourseName(String tcourseName) {
		this.tcourseName = tcourseName;
	}

	public Double getTcourseCredit() {
		return tcourseCredit;
	}

	public void setTcourseCredit(Double tcourseCredit) {
		this.tcourseCredit = tcourseCredit;
	}

	public Long getTotalTutorialMark() {
		return totalTutorialMark;
	}

	public void setTotalTutorialMark(Long totalTutorialMark) {
		this.totalTutorialMark = totalTutorialMark;
	}

	public Long getTotalFinalMark() {
		return totalFinalMark;
	}

	public void setTotalFinalMark(Long totalFinalMark) {
		this.totalFinalMark = totalFinalMark;
	}

	public String getTcourseType() {
		return tcourseType;
	}

	public void setTcourseType(String tcourseType) {
		this.tcourseType = tcourseType;
	}

	public TExam getTexam() {
		return texam;
	}

	public void setTexam(TExam texam) {
		this.texam = texam;
	}

	public Set<TStudent> getTstudents() {
		return tstudents;
	}

	public void setTstudents(Set<TStudent> tstudents) {
		this.tstudents = tstudents;
	}



}
