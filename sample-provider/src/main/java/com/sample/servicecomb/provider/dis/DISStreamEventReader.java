package com.sample.servicecomb.provider.dis;

import com.huaweicloud.dis.DIS;

/**
 * @ClassName DISStreamEventReader
 * @Description 以事件的方式读取DIS流
 * @Author Hyx
 * @DATE 2018/11/1 10:47
 */
public class DISStreamEventReader implements DISStreamReader{

    @Override
    public void reader(DIS dis, String streamName,Byte streamType) {
        System.out.println("执行事件读取流的任务...");
    }
}
