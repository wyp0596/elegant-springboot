package com.ajavac.consumer;

import com.ajavac.dto.TimeRequest;
import com.ajavac.dto.TimeResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by wyp0596 on 13/10/2017.
 */
@FeignClient(name = "time-provider", path = "time")
public interface TimeConsumer {

    @PostMapping("current_time")
    TimeResponse currentTime(@RequestBody TimeRequest timeRequest);

}
