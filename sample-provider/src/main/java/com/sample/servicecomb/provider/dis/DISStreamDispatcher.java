package com.sample.servicecomb.provider.dis;

import com.huaweicloud.dis.iface.data.response.Record;

/**
 * @ClassName DISStreamDispatcher
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/5 11:39
 */
public interface DISStreamDispatcher {

    /**
     * 数据采集
     * @param record
     * @param disStreamType
     * @param streamName
     */
    void dispatcher(String streamName, Byte disStreamType, Record record) throws Exception;

}
