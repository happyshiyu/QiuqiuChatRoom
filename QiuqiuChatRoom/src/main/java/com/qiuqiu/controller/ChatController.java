package com.qiuqiu.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiuqiu.netty.ChatConstants;
import com.qiuqiu.pojo.User;

import cn.qiuqiu.service.UserService;

@RestController
@RequestMapping("/chat")
public class ChatController {
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "users")
	public Map<String, Object> users(@RequestBody String json) {
		JSONObject jsonObject = JSON.parseObject(json);
		Long id = jsonObject.getLong("id");
		Map<String, User> onlines = ChatConstants.onlines;
		User user = userService.getById(id);
		Map<String, Object> map = new HashMap<String, Object>(2);	
		map.put("curName", user.getName());
		map.put("users", onlines);
		return map;
	}
}
