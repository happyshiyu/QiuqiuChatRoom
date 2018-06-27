package com.qiuqiu.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User implements Serializable{
	private static final long serialVersionUID = 3562768188264006800L;
	//用户ID
	@Id
	private Long id;
	//用户名
	@Column(nullable = false)
	private String username;
	//密码
	@Column(nullable = false)
	private String password;
	//姓名
	@Column(nullable = false)
	private String name;
	//头像地址
	@Column(nullable = false)
	private String headImg;
	
	public User() {
		
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", headImg="
				+ headImg + "]";
	}

	
}
