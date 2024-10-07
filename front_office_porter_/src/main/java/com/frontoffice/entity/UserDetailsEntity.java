package com.frontoffice.entity;
import org.apache.naming.java.javaURLContextFactory;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertFalse.List;
@Entity
@Table(name="USER_DTLS")
public class UserDetailsEntity {
	
	@Id
	@Column(name="userId",length=25)
	@GeneratedValue(strategy=GenerationType.AUTO)
	int userId;
	
	@Column(name="name")
	String name;
	
	@Column(name="email")
	String email;
	
	@Column(name="phone")
	long phone;
	
	@Column(name="pws")
	String pwd;
	
	@Column(name="accStatus")
	String accStatus;

	@OneToMany(mappedBy="user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private java.util.List<StudentEnqueryEntity> enquiries;
	
	public java.util.List<StudentEnqueryEntity> getEnquiries() {
		return enquiries;
	}

	public void setEnquiries(java.util.List<StudentEnqueryEntity> enquiries) {
		this.enquiries = enquiries;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getAccStatus() {
		return accStatus;
	}

	public void setAccStatus(String accStatus) {
		this.accStatus = accStatus;
	}

}
