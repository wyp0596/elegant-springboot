package com.ajavac;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Rest接口
 * Created by wyp0596 on 21/03/2017.
 */
@RestController
public class MainController {

    @GetMapping
    public Hello main() {
        return new Hello();
    }

    @GetMapping(path = "hello")
    public Hello hello(@RequestParam("name") String name) {
        return new Hello(new Date(System.currentTimeMillis()), "Hello " + name, 8080);
    }
}
