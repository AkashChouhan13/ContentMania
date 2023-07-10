package com.akash.project.event;

import org.springframework.context.ApplicationEvent;

import com.akash.project.entity.User;

public class RegistrationComEvent extends ApplicationEvent
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private User user;
	
	
	private String applicationUrl;
	
	public RegistrationComEvent(User user,String applicationUrl)
	{
		super(user);
		this.user = user;
		this.applicationUrl = applicationUrl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getApplicationUrl() 
	{
		return applicationUrl;
	}

	public void setApplicationUrl(String applicationUrl) {
		this.applicationUrl = applicationUrl;
	}
	
	
	

}
