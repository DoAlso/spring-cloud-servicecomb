package com.sample.servicecomb.common.bean;

import java.io.Serializable;

/**
 * @ClassName ResponseEntity
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/2/21 17:18
 */
public class ResponseEntity<T> implements Serializable {
    private String errorMsg;
    private String errorCode;
    private T data;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
