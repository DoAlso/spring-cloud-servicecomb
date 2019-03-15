package com.sample.servicecomb.common.bean.hw;

/**
 * @ClassName SuccessResult
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/3/15 16:58
 */
public class SuccessResult {
    public static final String SUCCESS_CODE = "FRS.0000";
    private String code;
    private String message;
    private Object object;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
