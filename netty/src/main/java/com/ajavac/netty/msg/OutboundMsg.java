package com.ajavac.netty.msg;

/**
 * 出站消息体
 * Created by wyp0596 on 14/04/2017.
 */
public class OutboundMsg {

    // 消息头
    private static final byte[] HEADER = new byte[]{(byte) 0xFF, 0x00, 0x00};
    // 总消息长度len
    // 消息类型
    private byte type;
    // 操作类型
    private byte opt;
    // 文本消息长度msgLen
    // 文本消息
    private String msgStr;
    // 二进制数据
    private byte[] data;
    // 校验位

    public OutboundMsg() {
    }

    public OutboundMsg(byte type, byte opt, String msgStr, byte[] data) {
        this.type = type;
        this.opt = opt;
        this.msgStr = msgStr;
        this.data = data;
    }

    public static byte[] getHEADER() {
        return HEADER;
    }

    public byte getType() {
        return type;
    }

    public byte getOpt() {
        return opt;
    }

    public String getMsgStr() {
        return msgStr;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "OutboundMsg{" +
                "type=" + type +
                ", opt=" + opt +
                ", msgStr='" + msgStr + '\'' +
                ", data.length=" + (data == null ? 0 : data.length) +
                '}';
    }
}
