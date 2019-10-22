package com.micro.fast.boot.starter.netty.websocket;

import com.micro.fast.boot.starter.netty.config.NettyConfig;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * 接收/处理/响应客户端websocket请求的核心业务处理类
 * @author lsy
 */
public class WebSocketHandler extends SimpleChannelInboundHandler<Object>{

    private WebSocketServerHandshaker handshaker;

    private static final String webSocketUrl = "ws://localhost:8888/websocket";
    /**
     * 客户端和服务端创建连接的时候调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        NettyConfig.group.add(ctx.channel());
    }

    /**
     * 客户端和服务端断开连接的时候调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        NettyConfig.group.remove(ctx.channel());
    }

    /**
     * 服务端接收客户端数据完成时候调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 工程出现异常的时候调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * 服务端处理客户端webSocket请求的核心方法
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        //处理客户端向服务端发起http握手请求的业务
        if (msg instanceof FullHttpRequest){
            handHttpRequestChannelHandler(channelHandlerContext,(FullHttpRequest)msg);
        }else if(msg instanceof WebSocketFrame){
            //处理websocket连接请求的业务
            handWebSocketFrameChannelHandler(channelHandlerContext,(WebSocketFrame)msg);
        }
    }

    /**
     * 处理http请求
     * @param ctx
     * @param request
     */
    private void handHttpRequestChannelHandler(ChannelHandlerContext ctx,FullHttpRequest request){
        if(request.getDecoderResult().isFailure() || "websocket".equals(request.headers().get("Upgrade"))){
            sendHttpResponse(ctx,request,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }else{
            WebSocketServerHandshakerFactory webSocketServerHandshakerFactory = new WebSocketServerHandshakerFactory(
                    webSocketUrl,null,false);
            //创建webSocket连接
            handshaker = webSocketServerHandshakerFactory.newHandshaker(request);
            if (handshaker == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            }else {
                handshaker.handshake(ctx.channel(),request);
            }
        }
    }

    /**
     * 服务端向客户端发送响应
     * @param ctx
     * @param request
     * @param response
     */
    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request
            , DefaultFullHttpResponse response){
        //判断请求是否成功
        if (response.getStatus().code()!= 200){
            // 返回请求
            ByteBuf byteBuf = Unpooled.copiedBuffer(response.getStatus().toString(), Charset.forName("UTF-8"));
            response.content().writeBytes(byteBuf);
            byteBuf.release();
        }
        ChannelFuture channelFuture = ctx.channel().writeAndFlush(response);
        if (response.getStatus().code()!=200){
            // 关闭连接
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * 处理客户端和服务端之间的webSocket业务
     * @param ctx
     * @param webSocketFrame
     */
    private void handWebSocketFrameChannelHandler(ChannelHandlerContext ctx,WebSocketFrame webSocketFrame){
        // 判断是否是关闭websocket连接请求
        if (webSocketFrame instanceof CloseWebSocketFrame){
            handshaker.close(ctx.channel(),(CloseWebSocketFrame)webSocketFrame.retain());
        }
        // 判断是否是pin消息
        if (webSocketFrame instanceof PingWebSocketFrame){
            ctx.channel().write(new PongWebSocketFrame(webSocketFrame.content().retain()));
            return;
        }
        // 判断是否是二进制消息，如果是二进制消息，抛出异常
        if (!(webSocketFrame instanceof TextWebSocketFrame)){
            throw  new RuntimeException("不支持二进制消息的传递");
        }
        // 返回消息
        TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) (webSocketFrame);
        //获取客户端向服务端发送的消息
        String text = textWebSocketFrame.text();
        System.out.println(text);
        TextWebSocketFrame textWebSocketFrame1 = new TextWebSocketFrame(new Date().toString()+":"+text);

        //服务端向每个连接上来的客户端群发消息
        NettyConfig.group.writeAndFlush(textWebSocketFrame1);

    }
}
