package com.akash.project.event;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.akash.project.entity.User;
import com.akash.project.service.EmailService;
import com.akash.project.service.UserServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class RegistrationCompleteEventListner implements ApplicationListener<RegistrationComEvent>
{

	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private EmailService serv;
	
    private static final Logger log = LoggerFactory.getLogger(RegistrationCompleteEventListner.class);

	
	@Override
	public void onApplicationEvent(RegistrationComEvent event) 
	{
		//create verification token for user with link-->
		User user  = event.getUser();
		String token = UUID.randomUUID().toString();
		
		//saving this tooken--->
		
		

		
		//send mail to user
		String url = event.getApplicationUrl() + "/verifyRegistration?token=" + token ;   
		
		log.info("message : {}",url);

		log.info("----> : {}",user);
		serv.sendEmail(user.getEmail(),"This is the registration confirmation link......",url);
		
		userService.saveVerificationTokenForUser(token,user);
		
	}

}
