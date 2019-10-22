package com.micro.fast.boot.starter.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * webSocket启动的入口
 * @author lsy
 */
public class WebSocketMain {
    public static void main(String[] args) {
        EventLoopGroup  bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new WebSocketChannelHandler());
            System.out.println("服务端开启等待客户端连接");
            Channel channel = serverBootstrap.bind(8888).sync().channel();
            channel.closeFuture().sync();
        }catch (Exception e){

        }finally {
            // 优雅的退出程序
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
