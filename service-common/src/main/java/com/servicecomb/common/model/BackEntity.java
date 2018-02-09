package com.servicecomb.common.model;

import java.io.Serializable;

/**
 * 封装接口调用返回的实体
 * @param <T>
 */
public class BackEntity<T> implements Serializable{

    private String resMsg;//返回的提示信息
    private String resCode;//返回的状态码
    private T resData;//返回的数据

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public T getResData() {
        return resData;
    }

    public void setResData(T resData) {
        this.resData = resData;
    }
}
