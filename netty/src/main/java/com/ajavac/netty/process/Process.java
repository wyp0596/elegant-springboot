package com.ajavac.netty.process;

import com.ajavac.netty.msg.InboundMsg;
import com.ajavac.netty.msg.OutboundMsg;

/**
 * 处理器接口
 * Created by wyp0596 on 16/04/2017.
 */
public interface Process {
    OutboundMsg process(InboundMsg inboundMsg);
}
