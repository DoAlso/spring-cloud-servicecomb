package com.sample.servicecomb.common.constant;

/**
 * @ClassName Constants
 * @Author huyax
 * @DATE 2019/4/28 13:38
 */
public class Constants {
    /**
     * 创建桶常量
     */
    public static final class BucketConstant {
        /** 桶ACL **/
        public static final Byte REST_CANNED_PRIVATE = 1;
        public static final Byte REST_CANNED_PUBLIC_READ = 2;
        public static final Byte REST_CANNED_PUBLIC_READ_WRITE = 3;
        public static final Byte REST_CANNED_PUBLIC_READ_DELIVERED = 4;
        public static final Byte REST_CANNED_PUBLIC_READ_WRITE_DELIVERED = 5;
        /** 桶存储模式 **/
        public static final Byte STANDARD = 1;
        public static final Byte WARM = 2;
        public static final Byte COLD = 3;
    }
}
