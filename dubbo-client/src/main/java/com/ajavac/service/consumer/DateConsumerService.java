package com.ajavac.service.consumer;

import com.ajavac.service.DateService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 日期消费者服务
 * Created by wyp0596 on 21/03/2017.
 */
@Service
public class DateConsumerService {

    @Reference(version = "1.0.0")
    private DateService dateService;

    public Date createTime(){
        return dateService.createTime();
    }

    public Date now(){
        return dateService.now();
    }
}
