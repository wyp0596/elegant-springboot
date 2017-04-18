package com.ajavac.netty.process;

import com.ajavac.netty.msg.InboundMsg;
import com.ajavac.netty.msg.OutboundMsg;
import org.springframework.stereotype.Component;

/**
 * 回声操作处理器
 * Created by wyp0596 on 18/04/2017.
 */
@Component
public class EchoProcess implements Process {


    @Override
    public OutboundMsg process(InboundMsg inboundMsg) {
        byte opt = inboundMsg.getOpt();
        switch (opt) {
            case 0x03:
                return echo(inboundMsg);
            case 0x04:
                return echo(inboundMsg);
            default:
                throw new UnsupportedOperationException("找不到对应的处理方法");
        }
    }

    private OutboundMsg echo(InboundMsg inboundMsg) {
        return new OutboundMsg(inboundMsg.getType(),
                inboundMsg.getOpt(), inboundMsg.getMsgStr(), null);
    }

}
