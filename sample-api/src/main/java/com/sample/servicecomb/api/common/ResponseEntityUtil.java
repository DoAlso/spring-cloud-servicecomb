package com.sample.servicecomb.api.common;

/**
 * @ClassName ResponseEntityUtil
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/2/21 17:24
 */
public class ResponseEntityUtil {

    public static final String SUCCESS_CODE = "0000";
    public static final String ERROR_CODE = "-1";

    public static <T> ResponseEntity<T> response(String code,String message,T data){
        ResponseEntity responseBody = new ResponseEntity();
        responseBody.setData(data);
        responseBody.setErrorCode(code);
        responseBody.setErrorMsg(message);
        return responseBody;
    }

    public static ResponseEntity response(String code,String message){
        ResponseEntity responseBody = new ResponseEntity();
        responseBody.setErrorCode(code);
        responseBody.setErrorMsg(message);
        return responseBody;
    }

    public static <T> ResponseEntity<T> success(String message,T data){
        return response(SUCCESS_CODE,message,data);
    }

    public static ResponseEntity success(String message){
        return response(SUCCESS_CODE,message);
    }

    public static ResponseEntity fail(String code,String message){
        return response(code, message);
    }

    public static ResponseEntity fail(String message) {
        return response(ERROR_CODE,message);
    }
}
