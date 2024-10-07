package com.frontoffice.binding;

public class EnquiryForm {
	String name;
	String classMode;
	String course;
	String enquiryStatu;
	long contactNumber;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(long contactNumber) {
		this.contactNumber = contactNumber;
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


}
