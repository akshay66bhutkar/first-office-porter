package com.frontoffice.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frontoffice.binding.LoginForm;
import com.frontoffice.binding.SingUpForm;
import com.frontoffice.binding.UnlockForm;
import com.frontoffice.entity.UserDetailsEntity;
import com.frontoffice.repository.IUserDetailsRepository;
import com.frontoffice.util.EmailUtil;
import com.frontoffice.util.PasswordUtil;

import jakarta.servlet.http.HttpSession;

@Service("userservice")
public class UserServiceImpl implements UserService {

	
	@Autowired
	HttpSession session;
	@Autowired
	private IUserDetailsRepository userDtRepository;
	@Autowired 
	EmailUtil emailUtil;
	@Override
	
	//SignUp Funcationality
	public boolean signUp(SingUpForm form) {
		//copy data from binding obj to entity obj
		UserDetailsEntity entity=new UserDetailsEntity();
		BeanUtils.copyProperties(form, entity);	
		//generate random pws and set to object
		String pws=PasswordUtil.generateRandompws();
		entity.setPwd(pws);
		//set account status as locked
		entity.setAccStatus("LOCKED");
		//todo:: insert record
		userDtRepository.save(entity);
		//todo:: send email to user to unlock the account
		String to=form.getEmail();
		String subject="Unlock Your Account ";
		StringBuffer body=new StringBuffer("");
		body.append("<h1> Use below temporary password to unlock your account</h1>");
		body.append("Temporary pwd : "+ pws);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8096/unlock?email="+to+"\">Click here to unclock your account</a>");
		return  emailUtil.sendEmail(to, subject, body.toString());
		
	}
	
	
	
	//UnlockedAccount Functionality
	@Override
	public boolean unlcokAccount(UnlockForm unlockform) {
		UserDetailsEntity userDetails=userDtRepository.findByEmail(unlockform.getEmail());
		
		if(userDetails.getPwd().equals(unlockform.getTempPwd())) {
			
			userDetails.setPwd(unlockform.getNewPwd());
			userDetails.setAccStatus("unlocked");
			userDtRepository.save(userDetails);
			return true;
			
		}else {
			return false;
		}	
	}



	//LoginAccount Functionality
	@Override
	public String loginAccount(LoginForm loginform) {
		//retrieve the record
		UserDetailsEntity userEntity=userDtRepository.findByEmailAndPwd(loginform.getEmail(),loginform.getPassword());
		
		//check retrieved record data is create or not
		if(userEntity == null) {
			return "Invalid Cretentials";
		}
		if(userEntity.getAccStatus().equals("locked")) {
			return "Your Account is in locked, You should unlocked it first";
		}
		
		//create session and store user data in session
		session.setAttribute("userId", userEntity.getUserId());
		return "success";
	}



	
	//ForgotPws Functionality
	@Override
	public String forgotPws(String email) {
		// retrieve user password based on email
		UserDetailsEntity userEntity=userDtRepository.findByEmail(email);		
		//check record available or not
		if(userEntity==null) {
			return "Invalid Email";
		}else {
			if(userEntity.getAccStatus().equals("LOCKED")) {
				return "Your account is in locked mode first unlock it";
			} 
		}
		
		String to=email;
		String subject ="Your Password ";
		String body=userEntity.getPwd();
		
		if(emailUtil.sendEmail(to, subject, body)) {
			return "Password has been send to email";
		}else {
			return"problem occured";
		}
	}

}
