package com.ajavac.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * 消息生产者
 * Created by wyp0596 on 05/06/2017.
 */
@Component
public class MsgProducer {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @PostConstruct
    public void send() {
        String context = "hello " + new Date();
        System.out.println("Sender : " + context);
        rabbitTemplate.convertAndSend("test-mq", context);
    }


}
