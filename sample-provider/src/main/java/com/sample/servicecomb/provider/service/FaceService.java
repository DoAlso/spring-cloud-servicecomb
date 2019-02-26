package com.sample.servicecomb.provider.service;

import com.huaweicloud.dis.iface.data.response.Record;
import com.sample.servicecomb.common.bean.ResponseEntity;
import com.sample.servicecomb.provider.model.FaceInfo;

public interface FaceService {
    /**
     * 记录人脸抓拍历史
     * @param streamName
     * @param record
     * @throws Exception
     */
    void faceCapture(String streamName, Record record) throws Exception;


    /**
     *
     * @return
     * @throws Exception
     */
    ResponseEntity createVipFace(FaceInfo faceInfo) throws Exception;
}
