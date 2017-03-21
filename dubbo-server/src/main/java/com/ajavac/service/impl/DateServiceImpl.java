package com.ajavac.service.impl;

import com.ajavac.service.DateService;
import com.alibaba.dubbo.config.annotation.Service;

import java.util.Date;

/**
 * 日期服务的实现
 * Created by wyp0596 on 21/03/2017.
 */
@Service(version = "1.0.0") //dubbo服务注解
public class DateServiceImpl implements DateService {

    private final Date createTime = new Date(System.currentTimeMillis());

    @Override
    public Date createTime() {
        return createTime;
    }

    @Override
    public Date now() {
        return new Date(System.currentTimeMillis());
    }
}
