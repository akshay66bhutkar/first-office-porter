package com.frontoffice.service;

import com.frontoffice.binding.LoginForm;
import com.frontoffice.binding.SingUpForm;
import com.frontoffice.binding.UnlockForm;

public interface UserService {


	
	public boolean signUp(SingUpForm from);
	
	public boolean unlcokAccount(UnlockForm unlcokform);
	
	public String loginAccount(LoginForm loginform);

	public String forgotPws(String email);
}
