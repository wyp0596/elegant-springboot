package com.ajavac.netty.handler;

import com.ajavac.netty.msg.InboundMsg;
import com.ajavac.netty.msg.OutboundMsg;
import com.ajavac.netty.process.ProcessCenter;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息处理器,使用单例模式
 * Created by wyp0596 on 14/04/2017.
 */
@Component
@ChannelHandler.Sharable
public class MsgHandler extends SimpleChannelInboundHandler<InboundMsg> {

    private static final Logger logger = LoggerFactory.getLogger(MsgHandler.class);

    private static final OutboundMsg illegalArgumentResult =
            new OutboundMsg((byte) 0x00, (byte) 0x65, "{}", null);

    @Autowired
    private ProcessCenter processCenter;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InboundMsg msg)
            throws Exception {
        Channel incoming = ctx.channel();
        if (logger.isDebugEnabled()) {
            logger.debug("入站消息:" + msg.toString());
        }

        //处理Msg返回结果
        OutboundMsg result = processCenter.process(msg);
        if (logger.isDebugEnabled()) {
            logger.debug("出站消息:" + result.toString());
        }

        //输出结果
        incoming.write(result);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // ctx.flush();
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).
                addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        logger.warn("发生异常", cause);
        if (cause instanceof IllegalArgumentException) {
            incoming.write(illegalArgumentResult);
        } else {
            ctx.close();
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                logger.warn("read idle");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                logger.warn("write idle");
            } else if (event.state() == IdleState.ALL_IDLE) {
                logger.warn("all idle");
            }
        }
    }
}
