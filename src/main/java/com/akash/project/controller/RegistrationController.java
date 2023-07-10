package com.akash.project.controller;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akash.project.entity.PasswordEntityToken;
import com.akash.project.entity.User;
import com.akash.project.entity.VerificationToken;
import com.akash.project.event.RegistrationComEvent;
import com.akash.project.model.PasswordModel;
import com.akash.project.model.UserModel;
import com.akash.project.service.EmailService;
import com.akash.project.service.UserServiceImpl;

@RestController
public class RegistrationController 
{
	
	private PasswordModel passwordModel;

	@Autowired
	private UserServiceImpl userService;
	
	@Autowired 
	private EmailService emailObj;
	
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping("/register")
	public String registerUser(@RequestBody UserModel userModel,final HttpServletRequest request)
	{
		User user = userService.registerUser(userModel);
		publisher.publishEvent(new RegistrationComEvent(
				
				user,
				applicationUrl(request)
				
				));
		return "----Success---";
	}
	
	
	
	@GetMapping("/verifyRegistration")
	public String verifyRegistration(@RequestParam("token") String token)
	{
		String result = userService.validateVerificationToken(token);
		if(result.equalsIgnoreCase("valid"))
		{
			return "User verified successfully";
		}
			
		return "Bad User";
	}

	private String applicationUrl(HttpServletRequest request) 
	{
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath(); 
	}




	@GetMapping("/resendVerifyToken")
	public String resendVerificationToken(@RequestParam("token") String oldToken,HttpServletRequest request)
	{
		
		
		VerificationToken verificationToken
		= userService.generateNewVerificationToken(oldToken);
		
		User user = verificationToken.getUser();				
		
		
		//send mail to user
		String url = "http://localhost:9090" + "/verifyRegistration?token=" + verificationToken.getToken() ;   

	

		emailObj.sendEmail(user.getEmail(),"new registration confirmation link......",url);
				
		return "verification link sent" ;
	}
	
	@PostMapping("/resetpassword")
	public String resetPassword(@RequestBody PasswordModel passwordModel)
	{
		
		
		User user = userService.findUserByEmail(passwordModel.getEmail());
		
		String url = "";   

		
		if(user != null)
		{
			String token = UUID.randomUUID().toString();
			PasswordEntityToken passwordEntityToken = userService.createPasswordResetTokenForUser(user,token);
			
			url = "http://localhost:9090" + "/savepassword?token=" + passwordEntityToken.getToken() ;   

			emailObj.sendEmail(user.getEmail(),"password reset link......",url);
			
			return "reset link sent" ;
			
		}
		
		return url;
	}
	
	
	@PostMapping("/savepassword")
	public String savePassword(@RequestParam("token") String token,@RequestBody PasswordModel passModel)
	{
		
		String result = userService.validatePasswordRestToken(token);
		if(!result.equalsIgnoreCase("valid"))
		{
			return "Invalid Token";
		}
		Optional<User> user = userService.getUserByPasswordResetToken(token);
		
		if(user.isPresent())
		{
			userService.changePassword(user.get(),passModel.getNewPassword());
			return "password reset  successfully";

		}
		else
		{
			return "invalid token";
		}
	}
	
	
	
}
