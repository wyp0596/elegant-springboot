package com.ajavac.controller;

import com.ajavac.dto.UserInfo;
import com.ajavac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest接口
 * Created by wyp0596 on 21/03/2017.
 */
@RestController
@RequestMapping("user")
public class MainController {

    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("insert")
    public UserInfo insert(@RequestBody UserInfo userInfo){
        UserInfo result = userService.insert(userInfo);
        return result;
    }

    @GetMapping("list")
    public List<UserInfo> list(){
        List<UserInfo> result = userService.findAll();
        return result;
    }

}
