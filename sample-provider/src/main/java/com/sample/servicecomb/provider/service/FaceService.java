package com.sample.servicecomb.provider.service;

import com.huaweicloud.dis.iface.data.response.Record;
import com.sample.servicecomb.common.bean.ResponseEntity;
import com.sample.servicecomb.provider.model.req.CreateFaceReq;
import com.sample.servicecomb.provider.model.req.CreateFaceSetReq;

public interface FaceService {
    /**
     * 记录人脸抓拍历史
     * @param streamName
     * @param record
     * @throws Exception
     */
    void faceCapture(String streamName, Record record) throws Exception;


    /**
     * 上传VIP人脸
     * @return
     * @throws Exception
     */
    ResponseEntity createVipFace(CreateFaceReq createFaceReq) throws Exception;


    /**
     * 创建VIP人脸库
     * @return
     * @throws Exception
     */
    ResponseEntity createFaceSet(CreateFaceSetReq createFaceSetReq) throws Exception;



    ResponseEntity deleteFaceSet(String faceSetName) throws Exception;
}
