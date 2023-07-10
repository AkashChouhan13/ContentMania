package com.akash.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akash.project.model.PasswordModel;

public interface PasswordModelRepo extends JpaRepository<PasswordModel, Long> 
{
	
	PasswordModel save(PasswordModel passwordModel);

}
