package com.akash.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akash.project.entity.Post;
import com.akash.project.entity.User;
import com.akash.project.repository.PostRepository;

@Service
public class PostService 
{

	@Autowired
	private PostRepository postRepository;
	
	public List<Post> findByUser(User user) 
	{
		  return postRepository.findByUser(user); 
	}

	public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

	public List<Post> getPostsFromOtherUsers(User currentUser) 
	{
        return postRepository.findByUserNot(currentUser);
	}

	public void deletePost(User currentUser, Long postId) 
	{
		postRepository.deleteById(postId);
	}


	public Post addPost(User user, String title,String content) 
	{
		Post post = new Post();
		post.setTitle(title);
        post.setContent(content);
        post.setUser(user);
        return postRepository.save(post);

	}

}
