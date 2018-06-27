package com.qiuqiu.netty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import com.qiuqiu.pojo.User;

import io.netty.util.AttributeKey;

public class ChatConstants {
    public static final AttributeKey<String> CHANNEL_TOKEN_KEY = AttributeKey.valueOf("netty.channel.token");  
	/**用来保存当前在线人员*/
	public static Map<String, User> onlines = new ConcurrentHashMap<String, User>();
	
	public static void addOnlines(String sessionId, User val) {
		onlines.putIfAbsent(sessionId, val);
	}
	
	public static void removeOnlines(String sessionId) {
		if(StringUtils.isNotBlank(sessionId) && onlines.containsKey(sessionId)){
			onlines.remove(sessionId);
		}
	}
	
	private static int[]imgPrefix = {1,2,3,4,5,6,7,8,9,10,11};
	
	public static String headImg() {
		int index = RandomUtils.nextInt(0, imgPrefix.length);
		return "/resources/img/head/"+imgPrefix[index]+".jpg";
	}
	
	
}
