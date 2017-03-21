package com.ajavac.service;

import com.ajavac.dto.UserInfo;

import java.util.List;

/**
 * 用户服务接口
 * Created by wyp0596 on 21/03/2017.
 */
public interface UserService{

    UserInfo insert(UserInfo userInfo);
    UserInfo updatePassword(UserInfo userInfo);
    UserInfo findByUsername(String username);
    List<UserInfo> findAll();
    int deleteById(Long id);

}
