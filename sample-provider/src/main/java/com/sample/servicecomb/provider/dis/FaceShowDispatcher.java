package com.sample.servicecomb.provider.dis;

import com.huaweicloud.dis.iface.data.response.Record;
import com.sample.servicecomb.provider.service.FaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName FaceShowDispatcher
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/2/26 15:35
 */
@Service
public class FaceShowDispatcher implements DISStreamDispatcher{

    @Autowired
    private FaceService faceService;

    @Override
    public void dispatcher(String streamName, Byte disStreamType, Record record) throws Exception {
        faceService.faceCapture(streamName, record);
    }
}
