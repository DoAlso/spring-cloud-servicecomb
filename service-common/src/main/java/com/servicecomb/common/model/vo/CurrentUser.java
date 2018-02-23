package com.servicecomb.common.model.vo;

import java.io.Serializable;

public class CurrentUser implements Serializable {
    private String token;
    private Long id;
    private String account;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
