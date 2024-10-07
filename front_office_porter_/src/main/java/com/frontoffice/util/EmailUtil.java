package com.frontoffice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtil {
	@Value("${spring.mail.username}")
	private String fromEmail;
	@Autowired
	private JavaMailSender mailSender;
	
	public boolean sendEmail(String to,String subject,String body) {
		boolean isSent=false;
		try {
			MimeMessage mimeMessage=mailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(mimeMessage);
				
			helper.setFrom(fromEmail);
			helper.setCc(to);
			helper.setSubject(subject);
			helper.setText(body,true);
			mailSender.send(mimeMessage);
			isSent=true;
		}catch(Exception e) {
			
		}
		
		return isSent;
	}

}
