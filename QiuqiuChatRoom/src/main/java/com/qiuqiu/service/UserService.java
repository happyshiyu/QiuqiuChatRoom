package com.qiuqiu.service;

import com.qiuqiu.pojo.User;

public interface UserService {
	public User login(String username, String password);
	public User getByUsername(User user);
	public void saveOrUpdate(User user);
	public User getById(long id);
}
