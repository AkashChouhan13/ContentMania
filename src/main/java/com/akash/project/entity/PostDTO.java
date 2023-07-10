package com.akash.project.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PostDTO 
{
	@Id
    private Long id;
    private String title;
    private String content;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public PostDTO(Long id, String title, String content) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
	}
    
	public PostDTO() {}
    
}
