package com.micro.fast.boot.starter.netty.config;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Getter;
import lombok.Setter;

/**
 * netty的全局配置类
 * @author lsy
 */
@Getter
@Setter
public class NettyConfig {
    /**
     * 存储每一个客户端接入进来的channel对象
     */
    public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


}
