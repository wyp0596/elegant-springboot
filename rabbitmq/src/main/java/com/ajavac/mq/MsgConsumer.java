package com.ajavac.mq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息消费者
 * Created by wyp0596 on 05/06/2017.
 */
@Component
@RabbitListener(queues = "test-mq")
public class MsgConsumer {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver  : " + hello);
    }


}
