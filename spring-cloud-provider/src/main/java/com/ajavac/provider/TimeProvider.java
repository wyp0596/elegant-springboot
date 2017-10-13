package com.ajavac.provider;

import com.ajavac.dto.TimeRequest;
import com.ajavac.dto.TimeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by wyp0596 on 13/10/2017.
 */
@RestController
@RequestMapping("time")
public class TimeProvider {

    private static final Logger logger = LoggerFactory.getLogger(TimeProvider.class);

    @PostMapping("current_time")
    public TimeResponse currentTime(@RequestBody TimeRequest timeRequest) {
        String name = timeRequest.getName();
        Date date = new Date();
        logger.info("get request : {}", timeRequest.getName());
        return new TimeResponse(name, date.toString(), date.getTime());
    }

}
