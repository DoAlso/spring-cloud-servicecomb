package com.sample.servicecomb.common.bean;

/**
 * @ClassName User
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/1/21 17:22
 */
public class User {
    private Long userId;
    private String username;
    private String account;
    private String password;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
