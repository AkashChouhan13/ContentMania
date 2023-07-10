package com.akash.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akash.project.entity.PasswordEntityToken;

@Repository
public interface PasswordResetRepo extends JpaRepository<PasswordEntityToken, Long>
{

	PasswordEntityToken findByToken(String token);

}
