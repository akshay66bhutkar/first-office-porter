package com.frontoffice.controller;



import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.frontoffice.binding.LoginForm;
import com.frontoffice.binding.SingUpForm;
import com.frontoffice.binding.UnlockForm;
import com.frontoffice.service.UserServiceImpl;

@Controller
public class UserController {
	@Autowired
	private UserServiceImpl userService;
	
	@PostMapping("/signup")
	public String handleSingUp( @ModelAttribute("user")  SingUpForm form,Map<String,String> map) {
		boolean status=userService.signUp(form);

		if(status) {
			map.put("succMsg","Check Your Email");
			//model.addAttribute("succMsg","Check Your Email");
		}else {
			map.put("errMsg","Problem occured");
			//model.addAttribute("errMsg","Problem occured");
		}
		return "signup";
	}
	
	@PostMapping("/unlock")
	public String unlockaccount(@ModelAttribute("unlockform") UnlockForm unlockform,Map<String,String> map) {
	
		if(unlockform.getConfirmPwd().equals(unlockform.getNewPwd())) {
			Boolean status=userService.unlcokAccount(unlockform);
			if(status) {
				map.put("successMessage","Your Account is created");
				
			}else {
				map.put("pwsError","Temprory Password Is not correct");
			}
		}else {
		   map.put("errorMessage","New Password and Confirm Password should be same");
		}
		return "unlock";
	}
	
	@GetMapping("/unlock")
	public String unlock(@RequestParam String email , Map<String,Object> map) {
		UnlockForm unlock=new UnlockForm();

	    unlock.setEmail(email);
	    
		map.put("unlockform", unlock);
		return "unlock";
	}
	@GetMapping("/signup")
	public String singUp(Map<String,Object> map) {
		map.put("user", new SingUpForm());
		
		//model.addAttribute("user",);
		return "signup";
	}
	
	
	
	
	@GetMapping("/login")
	public String loginPage(Map<String ,Object> map) {
		map.put("loginForm", new LoginForm());
		return "login";
	}
	
	@PostMapping("/login")
	public String loginForm(@ModelAttribute("loginForm") LoginForm loginform,Map<String,String> map) {
		String status =userService.loginAccount(loginform);
		if(status=="success") {
			return "redirect:/dashboard";
		}else {
			map.put("errorMsg", status);
		}
		return "login";
	}
	
	@GetMapping("/pwsforgot")
	public String pwsForget() {
		return "pwsforgot";
	}
	
	@PostMapping("/forgotpwd")
	public String forgotPwd(@RequestParam String email,Map<String,String> map) {
		String msg=userService.forgotPws(email);
		map.put("msg", msg);
		return "pwsforgot";
	}
	

}
