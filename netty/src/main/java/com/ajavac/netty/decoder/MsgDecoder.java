package com.ajavac.netty.decoder;

import com.ajavac.netty.msg.InboundMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * 消息解码器,切记不可使用单例模式
 * Created by wyp0596 on 14/04/2017.
 */
public class MsgDecoder extends LengthFieldBasedFrameDecoder {

    private static final Logger logger = LoggerFactory.getLogger(MsgDecoder.class);

    private static final int MAX_FRAME_LENGTH = 1024 * 1024;//1G
    private static final int LENGTH_FIELD_OFFSET = 3;
    private static final int LENGTH_FIELD_LENGTH = 4;


    public MsgDecoder() {
        super(MAX_FRAME_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected InboundMsg decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        //数据还不可读
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }
        //全部数据已准备完毕,长度正确性已校验

        //解析数据头
        byte[] readHeader = new byte[LENGTH_FIELD_OFFSET];
        frame.readBytes(LENGTH_FIELD_OFFSET).readBytes(readHeader);
        //数据头有误
        if (!Arrays.equals(readHeader, InboundMsg.getHEADER())) {
            logger.debug("数据头有误");
            throw new IllegalArgumentException("数据头有误");
        }
        //数据长度部分
        int len = frame.readInt();
        //待校验信息
        byte[] data2check = new byte[len - 1];
        frame.getBytes(LENGTH_FIELD_OFFSET + LENGTH_FIELD_LENGTH,
                data2check, 0, len - 1);
        //解析数据体
        //消息类型
        byte type = frame.readByte();
        //操作类型
        byte opt = frame.readByte();
        //文本消息长度
        int msgLen = frame.readableBytes() - 1;
        //文本消息
        byte[] msg = new byte[msgLen];
        frame.readBytes(msgLen).readBytes(msg);
        //校验位
        byte check = frame.readByte();

        //冗余校验
        byte calValue = calculate(data2check);
        if (calValue != check){
            logger.debug("校验值不匹配");
            throw new IllegalArgumentException("校验值不匹配");
        }

        String msgStr = new String(msg, Charset.forName("utf-8"));
        return new InboundMsg(type, opt, msgStr);
    }

    /**
     * 冗余校验
     *
     * @param data 待校验数据
     * @return 冗余校验值
     */
    private byte calculate(byte[] data) {
        byte result = (byte) 0x00;
        for (byte aData : data) {
            result = (byte) (result ^ aData);
        }
        return result;
    }

}
