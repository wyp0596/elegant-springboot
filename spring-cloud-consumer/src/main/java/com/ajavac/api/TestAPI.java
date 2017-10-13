package com.ajavac.api;

import com.ajavac.consumer.TimeConsumer;
import com.ajavac.dto.TimeRequest;
import com.ajavac.dto.TimeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wyp0596 on 13/10/2017.
 */
@RestController
public class TestAPI {


    @Autowired
    private TimeConsumer timeConsumer;

    @GetMapping("test")
    public TimeResponse currentTime(){
        return timeConsumer.currentTime(new TimeRequest("test"));
    }
}
