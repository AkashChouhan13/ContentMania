package com.akash.project.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ConditionalOnBean
public class PassworEncoder 
{
	public String encoding(String password)
	{
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
	}

}
