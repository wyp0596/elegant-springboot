package com.ajavac.netty.encoder;

import com.ajavac.netty.msg.OutboundMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * 消息编码器,使用单例模式
 * Created by wyp0596 on 14/04/2017.
 */
@Component
@ChannelHandler.Sharable
public class MsgEncoder extends MessageToByteEncoder<OutboundMsg> {

    //  1  + 1  +  4  +  1  =   7
    // type opt msgLen check 一共占据的字节长度
    private static final int OTHER_INFO_LENGTH = 7;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, OutboundMsg msg, ByteBuf out) throws Exception {


        byte type = msg.getType();
        byte opt = msg.getOpt();
        String msgStr = msg.getMsgStr();
        byte[] data = msg.getData();

        byte[] msgBytes = msgStr.getBytes(Charset.forName("utf-8"));

        // 文本消息长度
        int msgLength = msgBytes.length;
        // 二进制数据长度
        int dataLength = data == null ? 0 : data.length;

        // 总消息长度
        int allDataLength = msgLength + dataLength + OTHER_INFO_LENGTH;

        out.writeBytes(OutboundMsg.getHEADER());//输出头部标识
        out.writeInt(allDataLength);//其后所有的数据长度(字节)
        out.writeByte(type);//数据类型
        out.writeByte(opt);//操作类型
        out.writeInt(msgLength);//信息数据长度(字节)
        out.writeBytes(msgBytes);//信息字符串内容(已加密,字节)
        if (data != null) {
            out.writeBytes(data);//二进制数据
        }
        byte[] bytesToCheck = new byte[allDataLength - 1];
        out.getBytes(7, bytesToCheck);
        byte check = calculate(bytesToCheck);
        out.writeByte(check);//冗余校验值
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
