package com.akash.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	
	private static final String[] WhHIT_LIST_URLS = {
			
			"/hello",
			"/register",
			"/resendVerifyToken"
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		http.csrf().disable();
		http.cors().disable();

		http.authorizeRequests()
		.antMatchers(WhHIT_LIST_URLS).permitAll();
				
	}
	
	
}
