package com.frontoffice.binding;

import org.springframework.stereotype.Component;

@Component
public class SingUpForm {
	
	 public String name;
	 public long phone;
	 public String email;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
