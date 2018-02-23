package com.servicecomb.common.model.param;

import java.io.Serializable;

/**
 * 登录请求的参数对象
 */
public class LoginParam implements Serializable{
    private String account;
    private String password;
    private String yzm;//普通验证码
    private String telCode;//短信验证码
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

    public String getYzm() {
        return yzm;
    }

    public void setYzm(String yzm) {
        this.yzm = yzm;
    }

    public String getTelCode() {
        return telCode;
    }

    public void setTelCode(String telCode) {
        this.telCode = telCode;
    }
}
