package com.sample.servicecomb.provider.controller;

import com.sample.servicecomb.common.bean.ResponseEntity;
import com.sample.servicecomb.provider.model.req.CreateFaceReq;
import com.sample.servicecomb.provider.model.req.CreateFaceSetReq;
import com.sample.servicecomb.provider.model.req.DeleteFaceReq;
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
    public ResponseEntity createVipFace(@RequestBody CreateFaceReq createFaceReq) throws Exception {
        return faceService.createVipFace(createFaceReq);
    }


    @PostMapping("/createFaceSet")
    public ResponseEntity createFaceSet(@RequestBody CreateFaceSetReq createFaceSetReq) throws Exception {
        return faceService.createFaceSet(createFaceSetReq);
    }

    @PostMapping("/deleteFaceSet")
    public ResponseEntity deleteFaceSet(@RequestBody DeleteFaceReq deleteFaceReq) throws Exception {
        return faceService.deleteFaceSet(deleteFaceReq.getFaceSetName());
    }
}
