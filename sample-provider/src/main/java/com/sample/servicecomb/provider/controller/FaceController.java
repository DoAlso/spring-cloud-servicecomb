package com.sample.servicecomb.provider.controller;

import com.sample.servicecomb.api.common.ResponseEntity;
import com.sample.servicecomb.provider.model.vo.CreateFaceVO;
import com.sample.servicecomb.provider.model.vo.CreateFaceSetVO;
import com.sample.servicecomb.provider.model.vo.DeleteFaceVO;
import com.sample.servicecomb.provider.service.FaceService;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName FaceController
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/2/26 17:20
 */
@RestController
@RestSchema(schemaId = "faceController")
public class FaceController {
    @Autowired
    private FaceService faceService;

    @PostMapping("/createVipFace")
    public ResponseEntity createVipFace(@RequestBody CreateFaceVO createFaceReq) throws Exception {
        return faceService.createVipFace(createFaceReq);
    }


    @PostMapping("/createFaceSet")
    public ResponseEntity createFaceSet(@RequestBody CreateFaceSetVO createFaceSetReq) throws Exception {
        return faceService.createFaceSet(createFaceSetReq);
    }

    @PostMapping("/deleteFaceSet")
    public ResponseEntity deleteFaceSet(@RequestBody DeleteFaceVO deleteFaceReq) throws Exception {
        return faceService.deleteFaceSet(deleteFaceReq.getFaceSetName());
    }
}
