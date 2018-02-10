package com.servicecomb.common.constant;

public class Constant {

    /**
     * 封装请求返回的状态码
     */
    public static class ResponseCode {
        public static final String SYSTEM_BUSY = "-1";//系统繁忙
        public static final String REQUEST_OK = "0";//请求成功
        public static final String NEED_POST = "43001";//需要 POST 请求
        public static final String NEED_GET = "43002";//需要 GET 请求
        public static final String NEED_HTTPS = "43003";//需要 HTTPS 请求
        public static final String PARAM_ERROR = "50001";//参数错误
        public static final String TOKEN_ERROR = "50002";//Token错误
        public static final String NO_TOKEN = "50003";//请求头中没有TOKEN
        public static final String TOKEN_TIME_OUT = "50004";//TOKEN超时
    }


    /**
     * 请求返回的消息提示
     */
    public static class ResponseMSG {
        public static final String REQUEST_OK = "success";
        public static final String REQUEST_ERROR = "bad request";
    }

    /**
     * 通用的键值
     */
    public static class CommomKey {
        public static final String CURRENT_USER = "CURRENT_USER";
        public static final String CURRENT_USER_TOKEN = "X-Token";
    }
}
