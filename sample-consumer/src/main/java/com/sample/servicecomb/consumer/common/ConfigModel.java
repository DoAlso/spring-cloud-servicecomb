package com.sample.servicecomb.consumer.common;

import java.util.Map;

/**
 * @ClassName ConfigModel
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/4/12 18:12
 */
public class ConfigModel {
    private String message;
    private String code;
    private Map<String,Object> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
