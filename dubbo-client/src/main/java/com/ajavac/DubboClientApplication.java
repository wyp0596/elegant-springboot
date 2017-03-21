package com.ajavac;

import com.ajavac.service.consumer.DateConsumerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DubboClientApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DubboClientApplication.class, args);
        DateConsumerService dateConsumerService = context.getBean(DateConsumerService.class);
        System.out.println(dateConsumerService.now());
        System.out.println(dateConsumerService.createTime());
        context.close();
    }
}
