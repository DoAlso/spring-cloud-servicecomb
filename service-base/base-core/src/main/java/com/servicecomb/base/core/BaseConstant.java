package com.servicecomb.base.core;

public class BaseConstant {

    /**
     * base服务的请求响应信息
     */
    public static final class ResponseMSG {
        public static final String PHONE_USED = "this phone already exists";
    }

    /**
     * base服务的请求响应码
     */
    public static final class ResponseCode {
        public static final String PHONE_USED = "30001";//手机号已存在
    }
}
