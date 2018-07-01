package cn.qiuqiu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qiuqiu.dao.UserRepository;
import com.qiuqiu.pojo.User;

import cn.qiuqiu.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Override
	public User login(String username, String password) {
		return userRepository.getByUsernameAndPassword(username, password);
	}

	@Override
	public User getByUsername(User user) {
		return userRepository.getByUsername(user.getUsername());
	}

	@Override
	public void saveOrUpdate(User user) {
		userRepository.saveAndFlush(user);
	}

	@Override
	public User getById(long id) {
		return userRepository.getById(id);
	}

}
