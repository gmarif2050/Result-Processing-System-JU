package com.rps.entities.tabulation;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class Teacher {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="teacher_id_pk", nullable = false, updatable = false)
	private Long teacherId;
	private String name;
	private String dept;
	private String phone;
	private String email;
	private String password;
	
	@OneToMany(mappedBy="teacher",fetch=FetchType.LAZY)
	//@Cascade({CascadeType.ALL})
	private Set<TExam> texams = new HashSet<>();
	
	public Teacher(String name, String dept, String phone, String email, String password,
			Set<TExam> texams) {
		super();
		this.name = name;
		this.dept = dept;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.texams = texams;
	}

	public Teacher() { }

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<TExam> getTexams() {
		return texams;
	}

	public void setTexams(Set<TExam> texams) {
		this.texams = texams;
	}
	
}
