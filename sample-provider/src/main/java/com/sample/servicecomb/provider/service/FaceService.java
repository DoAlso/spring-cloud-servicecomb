package com.sample.servicecomb.provider.service;

import com.sample.servicecomb.api.common.ResponseEntity;
import com.sample.servicecomb.provider.model.vo.CreateFaceSetVO;
import com.sample.servicecomb.provider.model.vo.CreateFaceVO;

public interface FaceService {

    /**
     * 上传VIP人脸
     * @return
     * @throws Exception
     */
    ResponseEntity createVipFace(CreateFaceVO createFaceReq) throws Exception;


    /**
     * 创建VIP人脸库
     * @return
     * @throws Exception
     */
    ResponseEntity createFaceSet(CreateFaceSetVO createFaceSetReq) throws Exception;



    ResponseEntity deleteFaceSet(String faceSetName) throws Exception;
}
