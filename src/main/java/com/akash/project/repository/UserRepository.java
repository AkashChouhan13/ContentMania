package com.akash.project.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import com.akash.project.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>
{

	User findByEmail(String email);

	User findByFirstName(String firstName);
	
}
