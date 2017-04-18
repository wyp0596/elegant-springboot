package com.ajavac.netty.process;

import com.ajavac.netty.msg.InboundMsg;
import com.ajavac.netty.msg.OutboundMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息处理中心,处理入站信息,返回出站信息
 * Created by wyp0596 on 16/04/2017.
 */
@Component
public class ProcessCenter {

    @Autowired
    private EchoProcess echoProcess;

    public OutboundMsg process(InboundMsg inboundMsg) {
        byte type = inboundMsg.getType();
        switch (type){
            case 0x01:
                return echoProcess.process(inboundMsg);
            case 0x02:
                return echoProcess.process(inboundMsg);
            default:
                throw new UnsupportedOperationException("找不到对应的处理器");
        }
    }
}
