package com.akash.project.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.akash.project.entity.PasswordEntityToken;
import com.akash.project.entity.Post;
import com.akash.project.entity.User;
import com.akash.project.entity.VerificationToken;
import com.akash.project.model.UserModel;
import com.akash.project.repository.PasswordResetRepo;
import com.akash.project.repository.PostRepository;
import com.akash.project.repository.UserRepository;
import com.akash.project.repository.VerificationTokenRepo;

@Service
public class UserServiceImpl 
{	
	@Autowired
	private VerificationTokenRepo verificationTokenRepo;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordResetRepo passwordResetRepo;
	
	@Autowired
	private PostRepository postRepository;

	public User registerUser(UserModel userModel) 
	{
		User user = new User();
		user.setEmail(userModel.getEmail());
		user.setFirstName(userModel.getFirstName());
		user.setLastName(userModel.getLastName());
		user.setRole("USER");
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String passwordenc  = passwordEncoder.encode( userModel.getPassword());
		user.setPassword( passwordenc);

		userRepository.save(user);

		return user;
	}

	public void saveVerificationTokenForUser(String token, User user) 
	{
		VerificationToken verificationToken = new VerificationToken(user,token);
		
		verificationTokenRepo.save(verificationToken);	
	}

	public String validateVerificationToken(String token) 
	{
		VerificationToken verificationToken = verificationTokenRepo.findByToken(token);
		if(verificationToken == null)
		{
			return "invalid";
		}
		User user = verificationToken.getUser();
		Calendar cal = Calendar.getInstance();
		
		
		System.out.println((verificationToken.getExpirationTime().getTime() - cal.getTime().getTime()));
		if((verificationToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0)
		{
			verificationTokenRepo.delete(verificationToken);
			return "expired_____!";
		}
		
		user.setEnabled(true);
		userRepository.save(user);
		
		return "valid";
	}

	public VerificationToken generateNewVerificationToken(String oldToken) 
	{
		VerificationToken verificationToken = verificationTokenRepo.findByToken(oldToken);
		verificationToken.setToken(UUID.randomUUID().toString());
		verificationTokenRepo.save(verificationToken);
		return verificationToken;
	}

	public User findUserByEmail(String email) 
	{
		
		return userRepository.findByEmail(email);
	}

	public PasswordEntityToken createPasswordResetTokenForUser(User user, String token) 
	{
		PasswordEntityToken passwordEntityToken = new PasswordEntityToken(user,token);
		passwordResetRepo.save(passwordEntityToken);
		
		return passwordEntityToken;
	}

	public String validatePasswordRestToken(String token) 
	{
		PasswordEntityToken passwordto = passwordResetRepo.findByToken(token);
		if(passwordto == null)
		{
			return "invalid";
		}
		Calendar cal = Calendar.getInstance();
		
		
		System.out.println((passwordto.getExpirationTime().getTime() - cal.getTime().getTime()));
		if((passwordto.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0)
		{
			passwordResetRepo.delete(passwordto);
			return "expired_____!";
		}
				
		return "valid";
	}

	public Optional<User> getUserByPasswordResetToken(String token) 
	{
		return Optional.ofNullable(passwordResetRepo.findByToken(token).getUser());
	}

	public void changePassword(User user, String newPassword) 
	{
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}

	public User findByEmail(String email) 
	{
		User user = userRepository.findByEmail(email);
		return user;
	}

	public ArrayList<Post> getPostsFromOtherUsers(User currentUser) 
	{
        return postRepository.findByUserNot(currentUser);
    }

	public User getUserByUsername(String username) 
	{
        return userRepository.findByFirstName(username);
	}

	
}
