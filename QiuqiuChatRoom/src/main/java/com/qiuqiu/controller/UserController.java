package com.qiuqiu.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qiuqiu.dao.UserRepository;
import com.qiuqiu.pojo.User;

@Controller
public class UserController {
	@Autowired
	UserRepository userRepository;
	
	/**
	 * 跳转登录页面
	 * @return
	 */
	@RequestMapping(value = {"","/","index"}, method = RequestMethod.GET)
	public String index(){
		return "login.jsp";
	}
	
	/**
	 * 跳转注册页面
	 * @return
	 */
	@RequestMapping(value = "toRegister", method = RequestMethod.GET)
	public String toRegister(){
		return "register.jsp";
	}
	
	/**
	 * 跳转修改密码页面
	 * @return
	 */
	@RequestMapping(value = "toChange", method = RequestMethod.GET)
	public String toChange(){
		return "change.jsp";
	}
	
	/**
	 * 用户登录
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(String username, String password, HttpSession session ,HttpServletRequest request) {
		User user = userRepository.getByUsernameAndPassword(username, password);
		if(user != null) {
			session.setAttribute("token", user.getId());
			session.setAttribute("name", user.getName());
			return "chatroom.jsp";
		} else {
			request.setAttribute("msg", "用户名或密码错误");
			return "login.jsp";
		}	
	}
	
	/**
	 * 用户注册
	 * @return
	 */
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String register(User user, HttpServletRequest request) {
		User u = userRepository.getByUsername(user.getUsername());
		if(u == null) {
			user.setId(System.currentTimeMillis()/100);
			userRepository.save(user);
			request.setAttribute("msg", "注册成功");
			return "login.jsp";
		} else {
			request.setAttribute("msg", "用户名已存在");
			return "register.jsp";
		}
	}
	
	@RequestMapping(value = "test", method = RequestMethod.POST)
	public void test(User user) {
		User u = userRepository.getById(15300621413L);
		System.out.println(u);
		
	}
}