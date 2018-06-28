package com.qiuqiu.controller;


import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qiuqiu.dao.UserRepository;
import com.qiuqiu.pojo.User;
import com.qiuqiu.util.FastDFSClient;
import com.qiuqiu.util.Result;

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
	
	/**
	 * 修改密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "change", method = RequestMethod.POST)
	public Result change(String username, String oldpass, String newpass) {
		User user = userRepository.getByUsernameAndPassword(username, oldpass);
		if(user == null)
			return new Result(400,"用户名或密码错误",null);
		else {
			user.setPassword(newpass);
			userRepository.saveAndFlush(user);
			return new Result(200,"修改密码成功",null);
		}
			
	}
	
	@RequestMapping(value = "/imgUpdate", produces = "application/json; charset=utf-8", 
			method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseBody
    public Result imgUpdate(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            return new Result(400, "文件不能为空", null);
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
        try {
        	//创建一个FastDFS的客户端
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
			String path = fastDFSClient.uploadFile(file.getBytes(), extName);
			System.out.println("192.168.25.133:22122/" + path);
			return new Result(200, "文件上传成功", null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        return new Result(400, "文件上传失败", null);
    }

}