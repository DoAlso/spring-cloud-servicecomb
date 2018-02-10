package com.servicecomb.common.model.vo;

import java.io.Serializable;

public class CurrentUser implements Serializable {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
