package com.rps.entities.tabulation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.rps.entities.security.Authority;

@Entity
public class Teacher  implements UserDetails{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="teacher_id_pk", nullable = false, updatable = false)
	private Long teacherId;
	private String name;
	private String dept;
	private String phone;
	private String email;
	private String password;
	
	//@Transient
	private String username;
	
	
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

	public Teacher() {  }

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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//Empty set of GrantedAuthority Object
		Set<GrantedAuthority> authorities= new HashSet<>();
		
		//All roles of this user are added to the GrantedAuthority Set
		authorities.add(new Authority("ROLE_USER"));
		
		//the collection set of GrantedAuthority containing RoleName-Authority Object is returned then
		return authorities;
	}

	public String getUsername() {
		this.username = this.email;
		return email;
	}
	
	public void setUsername(String username) {
		this.username = username;
		this.email = username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
}
