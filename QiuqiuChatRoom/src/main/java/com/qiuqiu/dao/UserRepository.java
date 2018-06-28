package com.qiuqiu.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qiuqiu.pojo.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
	User getByUsernameAndPassword(String username, String password);
	
	User getByUsername(String username);
	
	User getById(Long id);
	

}
