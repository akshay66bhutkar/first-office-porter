package com.frontoffice.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.scheduling.quartz.LocalDataSourceJobStore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="student_enquries")

public class StudentEnqueryEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer enqIdInteger;
	
	private Long contactNumber;
	
	private String name;
	
	private String classMode;
	
	private String course;
	
	private String enquiryStatu;
	
	@CreationTimestamp
	private LocalDate dateCreate;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserDetailsEntity user;

	public Integer getEnqIdInteger() {
		return enqIdInteger;
	}

	public void setEnqIdInteger(Integer enqIdInteger) {
		this.enqIdInteger = enqIdInteger;
	}

	public Long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassMode() {
		return classMode;
	}

	public void setClassMode(String classMode) {
		this.classMode = classMode;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getEnquiryStatu() {
		return enquiryStatu;
	}

	public void setEnquiryStatu(String enquiryStatu) {
		this.enquiryStatu = enquiryStatu;
	}

	public LocalDate getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(LocalDate dateCreate) {
		this.dateCreate = dateCreate;
	}

	public UserDetailsEntity getUser() {
		return user;
	}

	public void setUser(UserDetailsEntity user) {
		this.user = user;
	}
	
   
	
	


}
