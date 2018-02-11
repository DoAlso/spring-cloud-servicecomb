package com.servicecomb.common.model.vo;

import java.io.Serializable;

public class CurrentUser implements Serializable {
    private String token;
    private String account;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
