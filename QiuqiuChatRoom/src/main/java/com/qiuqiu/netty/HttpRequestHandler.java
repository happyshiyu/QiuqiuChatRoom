package com.qiuqiu.netty;

import java.io.RandomAccessFile;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.qiuqiu.dao.UserRepository;
import com.qiuqiu.pojo.User;
import com.qiuqiu.util.SpringUtil;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	private static UserRepository userRepository;
	static {
		userRepository = SpringUtil.getBean(UserRepository.class);
	}
	
	private final String webUri;
	private final String INDEX = "E:\\oworkspace\\test\\src\\main\\webapp\\index.html";
	
	public HttpRequestHandler(String webUri) {
		this.webUri = webUri;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		System.out.println(request.uri());
		String uri = StringUtils.substringBefore(request.uri(), "?");
		//如果是websocket请求
		if(webUri.equalsIgnoreCase(uri)) {
			QueryStringDecoder query = new QueryStringDecoder(request.uri());
			Map<String, List<String>> map = query.parameters();
			List<String> tokens = map.get("token");
			//根据参数保存当前登录对象, 并把该token加入到channel中,这里的token实为用户id
			if(tokens != null && !tokens.isEmpty()) {
				String token = tokens.get(0);
				long id = Long.valueOf(token);
				User user = userRepository.getById(id);
				ChatConstants.addOnlines(token, user);
				ctx.channel().attr(ChatConstants.CHANNEL_TOKEN_KEY).getAndSet(token);
			}
			request.setUri(uri);
			ctx.fireChannelRead(request.retain());
		}else {
			//正常Http请求
			if(HttpUtil.is100ContinueExpected(request)) {
				send100ContinueExpected(ctx);
			}
			
			RandomAccessFile file = new RandomAccessFile(INDEX, "r");
			HttpResponse response = new DefaultHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
			response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
			
			boolean keepAlive = HttpUtil.isKeepAlive(request);
			if(keepAlive) {
				response.headers().set(HttpHeaderNames.CONTENT_LENGTH, file.length());
				response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
			}
			ctx.write(response);
			
			if(ctx.pipeline().get(SslHandler.class) == null) {
				ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
			}else {
				ctx.write(new ChunkedNioFile(file.getChannel()));
			}
			
			ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
			if(!keepAlive) {
				future.addListener(ChannelFutureListener.CLOSE);
			}
			
			file.close();
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	private void send100ContinueExpected(ChannelHandlerContext ctx) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONFLICT);
		ctx.writeAndFlush(response);		
	}
}
