package com.akash.project.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akash.project.entity.Post;
import com.akash.project.entity.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>
{
    ArrayList<Post> findByUser(User user);

	ArrayList<Post> findByUserNot(User currentUser);

}
