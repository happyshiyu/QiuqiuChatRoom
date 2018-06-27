package com.qiuqiu.pojo;

import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.qiuqiu.netty.ChatConstants;

public class ChatMessage {
	//发送消息
	private User from;
	
	//发送内容
	private String message;
	
	//接收者列表
	private Map<String, User> to;
	
	//发送时间
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	public ChatMessage() {
		
	}
	
	public ChatMessage(User from,String message) {
		this.from = from;
		this.message = message;
		this.to = ChatConstants.onlines;
		this.createTime = new Date();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public Map<String, User> getTo() {
		return to;
	}

	public void setTo(Map<String, User> to) {
		this.to = to;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
