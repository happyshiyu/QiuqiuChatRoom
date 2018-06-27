package com.qiuqiu.netty;

import java.util.concurrent.TimeUnit;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class ChatServerInitializer extends ChannelInitializer<Channel>{
	private final ChannelGroup group;
	
	public ChatServerInitializer(ChannelGroup group) {
		this.group = group;
	}
	
	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		//处理日志
		pipeline.addLast(new LoggingHandler(LogLevel.INFO));
		
		//处理心跳
		pipeline.addLast(new IdleStateHandler(0, 0, 1800, TimeUnit.SECONDS));
		pipeline.addLast(new ChatHeartbeatHandler());
		
		//将字节解码为HTTP编码
		pipeline.addLast(new HttpServerCodec());
		//向客户端发送HTML
		pipeline.addLast(new ChunkedWriteHandler());
		//应该是，应该是，应该是最大分片长度
		pipeline.addLast(new HttpObjectAggregator(64 * 1024));
		//自己写的Http适配器
		pipeline.addLast(new HttpRequestHandler("/ws"));
		//WebSocket协议中的握手用    只要访问/ws就会被拦截，如果控制帧...，如果是数据帧，放行给下面的自定义适配器
		pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
		//处理 TextWebSocketFrame 和握手完成事件
		pipeline.addLast(new TextWebSocketFrameHandler(group));		
	}
}
