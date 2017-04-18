package com.ajavac.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty启动工厂
 * Created by wyp0596 on 15/04/2017.
 */
public class NettyServerFactory {

    public static void create(EventLoopGroup bossGroup, EventLoopGroup workerGroup,
                                         ChannelInitializer<SocketChannel> channelInitializer, int port) throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup).
                channel(NioServerSocketChannel.class).
                childHandler(channelInitializer).
                option(ChannelOption.SO_BACKLOG, 1024).//最大连接数
                childOption(ChannelOption.SO_KEEPALIVE, true);

        bootstrap.bind(port).sync();
    }
}
