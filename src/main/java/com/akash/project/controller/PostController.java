package com.akash.project.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akash.project.entity.Post;
import com.akash.project.entity.PostDTO;
import com.akash.project.entity.User;
import com.akash.project.service.PostService;
import com.akash.project.service.UserServiceImpl;

@RestController
@RequestMapping(value="/app")
public class PostController 
{

	@Autowired
	private PostService postService;
	 
	
	@GetMapping("/allPosts")
	public List<PostDTO> getPostsFromOtherUsers(HttpSession session) {
	    User currentUser = (User) session.getAttribute("user");

	    if (currentUser == null) {
	        throw new IllegalArgumentException("User not logged in");
	    }

	    List<Post> allPosts = postService.getPostsFromOtherUsers(currentUser);
	    List<PostDTO> otherUserPosts = new ArrayList<>();

	    if (allPosts.isEmpty()) {
	        PostDTO postDTO = new PostDTO();
	        postDTO.setContent("Currently no post is available");
	        otherUserPosts.add(postDTO);
	    } else {
	        for (Post post : allPosts) {
	            if (!post.getUser().equals(currentUser)) {
	                PostDTO postDTO = new PostDTO();
	                postDTO.setId(post.getId());
	                postDTO.setContent(post.getContent());
	    	        postDTO.setTitle(post.getTitle());

	                // Set other properties as needed
	                otherUserPosts.add(postDTO);
	            }
	        }
	    }

	    return otherUserPosts;
	}
	
	
	
	
	
    @DeleteMapping("/delete/{postId}")
    public void deletePost(HttpSession session, @PathVariable Long postId) {
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            throw new IllegalArgumentException("User not logged in");
        }

        postService.deletePost(currentUser, postId);
    }

    @PostMapping("/newPost")
    public String addPost(HttpSession session, @RequestBody Post postRequest) {
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            throw new IllegalArgumentException("User not logged in");
        }

         postService.addPost(currentUser, postRequest.getTitle() ,postRequest.getContent());
         
         return "added successfully";
    }    
    
    
	@GetMapping("/myPosts")
	public List<PostDTO> getUserPosts(HttpSession session) {
	    User currentUser = (User) session.getAttribute("user");

	    if (currentUser == null) {
	        throw new IllegalArgumentException("User not logged in");
	    }

	    List<Post> userPosts = postService.findByUser(currentUser);
	    List<PostDTO> userPostDTOs = new ArrayList<>();

	    for (Post post : userPosts) {
	        PostDTO postDTO = new PostDTO();
	        postDTO.setId(post.getId());
	        postDTO.setContent(post.getContent());
	        postDTO.setTitle(post.getTitle());
	        
	        // Set other properties as needed
	        userPostDTOs.add(postDTO);
	    }

	    return userPostDTOs;
	}

    
    
}
