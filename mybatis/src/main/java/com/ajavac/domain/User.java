package com.ajavac.domain;

import com.ajavac.dto.UserInfo;

import java.sql.Timestamp;

/**
 * 用户domain层
 * Created by wyp0596 on 21/03/2017.
 */
public class User {

    private Long id;

    private String username;

    private String password;

    private Timestamp createAt;

    public User() {
    }

    public User(UserInfo userInfo) {
        this(userInfo.getUsername(),userInfo.getPassword());
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.createAt = new Timestamp(System.currentTimeMillis());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }
}
