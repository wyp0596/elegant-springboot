package com.ajavac.netty.initializer;

import com.ajavac.netty.decoder.MsgDecoder;
import com.ajavac.netty.encoder.MsgEncoder;
import com.ajavac.netty.handler.MsgHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通道初始化信息,单例
 * Created by wyp0596 on 15/04/2017.
 */
@Component
public class MyInitializer extends ChannelInitializer<SocketChannel> {

    private static final Logger logger = LoggerFactory.getLogger(MyInitializer.class);

    @Autowired
    private MsgHandler msgHandler;
    @Autowired
    private MsgEncoder msgEncoder;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        if (logger.isInfoEnabled()) {
            logger.info("来自" + ch.remoteAddress().getAddress() + "的连接");
        }
        ch.pipeline()
                .addLast("idleStateHandler",
                        new IdleStateHandler(120, 120, 240))
                .addLast("decoder", new MsgDecoder())
                .addLast("encoder", msgEncoder)
                .addLast(msgHandler);
    }
}
