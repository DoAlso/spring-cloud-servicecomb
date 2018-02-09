package com.servicecomb.common.model;

import java.io.Serializable;

/**
 * 封装请求参数实体
 * @param <T>
 */
public class BaseParam<T> implements Serializable {
    private T params;//请求参数的泛型
    private String reqCode;//请求码，用于标识本次请求的接口
    private String deviceCode;//设备编码，用于标识发起请求的设备
    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }

    public String getReqCode() {
        return reqCode;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }
}
