package com.akash.project.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.akash.project.UserLoginRequest;
import com.akash.project.entity.Post;
import com.akash.project.entity.PostDTO;
import com.akash.project.entity.User;
import com.akash.project.service.PostService;
import com.akash.project.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping(value="/app")
public class LoginController 
{

	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private PostService postService;
	
	
    private final ObjectMapper objectMapper;

    public LoginController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

	
	@PostMapping("/login")
	@ResponseBody
	public String login(HttpSession session, @RequestBody UserLoginRequest loginRequest) {
	    User user = userService.findByEmail(loginRequest.getEmail());

	    if (user == null) 
	    {
            throw new UsernameNotFoundException("User not found");

	    }

	    String password = loginRequest.getPassword();
	    boolean passwordMatches = new BCryptPasswordEncoder().matches(password, user.getPassword());

	    if (!passwordMatches) {
	        throw new IllegalArgumentException("Invalid username or password");
	    }

	    session.setAttribute("user", user);
	    
	    
	    return "welcome user";
	}

	
	
	
    @GetMapping("/logout")
    public String logout(HttpSession session) 
    {
        session.invalidate(); // Invalidate the session, removing all attributes
        return "redirect:/login"; // Redirect the user back to the login page
    }

    
    

}
