package com.ajavac.service.impl;

import com.ajavac.dao.UserDao;
import com.ajavac.domain.User;
import com.ajavac.dto.UserInfo;
import com.ajavac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户服务实现类
 * Created by wyp0596 on 21/03/2017.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public UserInfo insert(UserInfo userInfo) {
        User user = new User(userInfo);
        int result = userDao.insert(user);
        return new UserInfo(user);
    }

    @Override
    public UserInfo updatePassword(UserInfo userInfo) {
        return null;
    }

    @Override
    public UserInfo findByUsername(String username) {
        return null;
    }

    @Override
    public List<UserInfo> findAll() {
        List<User> userList = userDao.find();
        List<UserInfo> userInfoList = new ArrayList<>();
        for (User user : userList){
            userInfoList.add(new UserInfo(user));
        }
        return userInfoList;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }
}
