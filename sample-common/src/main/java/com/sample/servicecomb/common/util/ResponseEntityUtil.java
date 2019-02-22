package com.sample.servicecomb.common.util;

import com.sample.servicecomb.common.bean.ResponseEntity;

/**
 * @ClassName ResponseEntityUtil
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/2/21 17:24
 */
public class ResponseEntityUtil {

    public static ResponseEntity response(String errorMsg,String errorCode,Object data){
        ResponseEntity responseBody = new ResponseEntity();
        responseBody.setData(data);
        responseBody.setErrorCode(errorCode);
        responseBody.setErrorMsg(errorMsg);
        return responseBody;
    }
}
