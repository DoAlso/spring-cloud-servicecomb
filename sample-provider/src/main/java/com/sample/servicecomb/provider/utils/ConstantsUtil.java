package com.sample.servicecomb.provider.utils;

/**
 * @ClassName ConstantsUtil
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/2/13 14:52
 */
public class ConstantsUtil {
    public static final class OBS {
        public static final Integer HTTP_OK = 200;
        public static final String BUCKET_NAME = "hoolink-bucket";
        public static final String FACE_SET_BUCKET = "hoolink-face";
    }

    public static final class CommonKey {
        public static final String API_PACKAGE = "com.hoolink.ability.controller";
        public static final Integer HTTP_CODE_SUCCESS = 200;
        public static final Integer MULTIPLE = 2;
        public static final Integer XSD_MULTIPLE = 100;
        public static Integer AITHRESHOLD = 70;
        public static final String PROTOCOL = "https://";
        public static final String DELIMITER = "/";
        public static final String GET_DELIMITER = "?";
        public static final String GET_PLACEHOLDER = "&";
        public static final String EQUAL_SIGN = "=";
        public static final String POINT_SIGN = ".";
        public static final Integer NOTMAL_SIZE = 1;
    }
}
