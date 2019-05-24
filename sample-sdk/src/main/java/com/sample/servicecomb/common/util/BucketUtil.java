package com.sample.servicecomb.common.util;

import com.obs.services.model.AccessControlList;
import com.obs.services.model.StorageClassEnum;
import com.sample.servicecomb.common.constant.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @ClassName ObsUtil
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/4/28 13:47
 */
public class BucketUtil {

    /**
     * 获取ACL
     * @param value
     * @return
     */
    public static AccessControlList getAccessControlList(Byte value){
        if(Constants.BucketConstant.REST_CANNED_PUBLIC_READ_WRITE.equals(value)){
            return AccessControlList.REST_CANNED_PUBLIC_READ_WRITE;
        }
        if(Constants.BucketConstant.REST_CANNED_PRIVATE.equals(value)){
            return AccessControlList.REST_CANNED_PRIVATE;
        }
        if(Constants.BucketConstant.REST_CANNED_PUBLIC_READ.equals(value)){
            return AccessControlList.REST_CANNED_PUBLIC_READ;
        }
        if(Constants.BucketConstant.REST_CANNED_PUBLIC_READ_DELIVERED.equals(value)){
            return AccessControlList.REST_CANNED_PUBLIC_READ_DELIVERED;
        }
        if(Constants.BucketConstant.REST_CANNED_PUBLIC_READ_WRITE_DELIVERED.equals(value)){
            return AccessControlList.REST_CANNED_PUBLIC_READ_WRITE_DELIVERED;
        }
        return AccessControlList.REST_CANNED_PUBLIC_READ_WRITE;
    }

    /**
     * 获取桶的存储类型
     * @param value
     * @return
     */
    public static StorageClassEnum getStorageClassEnum(Byte value){
        if(Constants.BucketConstant.STANDARD.equals(value)){
            return StorageClassEnum.STANDARD;
        }
        if(Constants.BucketConstant.WARM.equals(value)){
            return StorageClassEnum.WARM;
        }
        if(Constants.BucketConstant.COLD.equals(value)){
            return StorageClassEnum.COLD;
        }
        return StorageClassEnum.STANDARD;
    }


    /**
     * 创建objectkey
     * @param fileExt
     * @return
     */
    public static String generateRandomFilename(String fileExt){
        Random rand = new Random();
        int random = rand.nextInt();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String temp = sf.format(new Date());
        String randomFilename = new StringBuffer(temp)
                .append(String.valueOf(random > 0 ? random : ( -1) * random))
                .append(".")
                .append(fileExt).toString();
        return randomFilename;
    }
}
