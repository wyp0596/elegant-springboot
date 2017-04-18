package com.ajavac.netty.server;

import com.ajavac.netty.NettyServerFactory;
import com.ajavac.netty.initializer.MyInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class NettyServer {
    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    @Autowired
    private MyInitializer myInitializer;

    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    //端口号
    @Value("${netty.port:5151}")
    private int port;

    @PostConstruct
    private void run() {
        try {
            NettyServerFactory.create(bossGroup, workerGroup, myInitializer, port);
            if (logger.isInfoEnabled()) {
                logger.info("Netty启动成功,绑定端口:" + port);
            }
        } catch (InterruptedException e) {
            logger.debug("Netty启动过程被中断");
        }
    }

    /**
     * 优雅销毁Netty
     */
    @PreDestroy
    private void destroy() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}
