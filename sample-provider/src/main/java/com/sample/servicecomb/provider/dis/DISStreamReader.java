package com.sample.servicecomb.provider.dis;

import com.huaweicloud.dis.DIS;

/**
 * @author Hyx
 */
public interface DISStreamReader {

   /**
    * 策略模式读取DIS
    * 中的流
    * @param dis
    * @param streamName
    * @param streamType
    */
   void reader(DIS dis, String streamName, Byte streamType);

}
