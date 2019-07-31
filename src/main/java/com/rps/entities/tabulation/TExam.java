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
public class TExam{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="texam_id_pk", nullable = false, updatable = false)
	private Long texamId;
	private Long texamNumber;
	private String texamName;
	private String session;
	private String programCode;
	private boolean resultPublished;
	
	@ManyToOne
	@JoinColumn(name = "teacher_id_fk")
	private Teacher teacher;
	
	@OneToMany(mappedBy="texam", fetch=FetchType.LAZY)
	//@Cascade({CascadeType.ALL})
	private Set<TCourse> tcourses = new HashSet<>();

	public TExam() {texamNumber=(long) 0; resultPublished=false;}

	public TExam(Long texamId, Long texamNumber, String texamName, String session, String programCode, Teacher teacher,
			Set<TCourse> tcourses) {
		super();
		this.texamId = texamId;
		this.texamNumber = texamNumber;
		this.texamName = texamName;
		this.session = session;
		this.programCode = programCode;
		this.teacher = teacher;
		this.tcourses = tcourses;
	}
	

	public boolean isResultPublished() {
		return resultPublished;
	}

	public void setResultPublished(boolean resultPublished) {
		this.resultPublished = resultPublished;
	}

	public Long getTexamId() {
		return texamId;
	}

	public void setTexamId(Long texamId) {
		this.texamId = texamId;
	}

	public Long getTexamNumber() {
		return texamNumber;
	}

	public void setTexamNumber(Long texamNumber) {
		this.texamNumber = texamNumber;
	}

	public String getTexamName() {
		return texamName;
	}

	public void setTexamName(String texamName) {
		this.texamName = texamName;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Set<TCourse> getTcourses() {
		return tcourses;
	}

	public void setTcourses(Set<TCourse> tcourses) {
		this.tcourses = tcourses;
	}
	
	

}
