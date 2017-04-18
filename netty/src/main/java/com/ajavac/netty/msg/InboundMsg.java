package com.ajavac.netty.msg;

/**
 * 入站消息体
 * Created by wyp0596 on 14/04/2017.
 */
public class InboundMsg {

    // 消息头
    private static final byte[] HEADER = new byte[]{(byte) 0xFF, 0x00, 0x00};
    // 总消息长度len
    // 消息类型
    private byte type;
    // 操作类型
    private byte opt;
    // 文本消息
    private String msgStr;
    // 校验位

    public InboundMsg() {
    }

    public InboundMsg(byte type, byte opt, String msgStr) {
        this.type = type;
        this.opt = opt;
        this.msgStr = msgStr;
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

    @Override
    public String toString() {
        return "InboundMsg{" +
                "type=" + type +
                ", opt=" + opt +
                ", msgStr='" + msgStr + '\'' +
                '}';
    }
}
