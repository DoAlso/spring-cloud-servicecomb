package com.sample.servicecomb.provider.controller;

import com.sample.servicecomb.common.bean.ResponseEntity;
import com.sample.servicecomb.provider.model.FaceInfo;
import com.sample.servicecomb.provider.service.FaceService;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity createVipFace(@RequestBody FaceInfo faceInfo) throws Exception {
        return faceService.createVipFace(faceInfo);
    }
}
