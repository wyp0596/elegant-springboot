package com.ajavac.controller;

import com.ajavac.dto.Hello;
import com.ajavac.dto.JSONResponse;
import com.ajavac.enums.ErrorCodeEnum;
import com.ajavac.exception.MyRuntimeException;
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
    public JSONResponse<Hello> main() {
        return new JSONResponse<>(new Hello());
    }

    @GetMapping(path = "hello")
    public JSONResponse<Hello> hello(@RequestParam("name") String name) {
        return new JSONResponse<>(new Hello(new Date(System.currentTimeMillis()), "Hello " + name, 8080));
    }

    @GetMapping(path = "exception1")
    public JSONResponse<Hello> exception1() {
        throw new MyRuntimeException(ErrorCodeEnum.NOT_FOUND);
    }

    @GetMapping(path = "exception2")
    public JSONResponse<Hello> exception2() throws Exception {
        throw new Exception("test");
    }

}
