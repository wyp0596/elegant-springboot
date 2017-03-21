package com.ajavac.dto;

import com.ajavac.domain.User;

import java.sql.Timestamp;

/**
 * 用户信息dto层
 * Created by wyp0596 on 21/03/2017.
 */
public class UserInfo {

    private Long id;

    private String username;

    private String password;

    private Timestamp createAt;

    public UserInfo() {
    }

    public UserInfo(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.createAt = user.getCreateAt();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }
}
