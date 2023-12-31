package com.akash.project.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "password_entity_token")
public class PasswordEntityToken 
{
	 private static final int EXPIRATION_TIME = 10;
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String token;
	
	private Date expirationTime;
	
	@OneToOne(fetch= FetchType.EAGER)
	@JoinColumn
	(
			name = "user_id",
			nullable = false,
			foreignKey = @ForeignKey(name="FK_USER_PASSWORD_TOKEN")
	)
	
	private User user;
	
	 public PasswordEntityToken(User user,String token)
		{
			 super();
			this.token = token;
			this.user = user;
			this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
		}
	
	
	public PasswordEntityToken(String token)
	{
		 super();
		this.token = token;
		this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
	}



	Date calculateExpirationDate(int expirationTime) 
	{
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTimeInMillis(new Date().getTime());
		calendar.add(Calendar.MINUTE,expirationTime);
		
		return new Date(calendar.getTime().getTime());
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public Date getExpirationTime() {
		return expirationTime;
	}


	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public PasswordEntityToken(Long id, String token, Date expirationTime, User user) {
		super();
		this.id = id;
		this.token = token;
		this.expirationTime = expirationTime;
		this.user = user;
	}

	public PasswordEntityToken() {}





}
