package com.sample.servicecomb.provider.controller;

import com.sample.servicecomb.api.common.ResponseEntity;
import com.sample.servicecomb.provider.model.vo.CompleteMultipartVO;
import com.sample.servicecomb.provider.model.vo.CreateBucketVO;
import com.sample.servicecomb.provider.model.vo.FileVO;
import com.sample.servicecomb.provider.service.FileService;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.core.MediaType;

/**
 * @ClassName FileController
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/2/13 16:28
 */

@RestController
@RestSchema(schemaId = "fileController")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/claimUploadId")
    public ResponseEntity claimUploadId(@RequestBody FileVO fileReq) throws Exception {
        return fileService.claimUploadId(fileReq.getFileName());
    }

    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA)
    public ResponseEntity upload(@RequestPart("file") MultipartFile multipartFile,String fileName,Integer partNum, String uploadId) throws Exception {
        return fileService.upload(multipartFile,fileName,partNum,uploadId);
    }

    @PostMapping(value = "/completeMultipartUpload")
    public ResponseEntity completeMultipartUpload(@RequestBody CompleteMultipartVO completeMultipartReq) throws Exception {
        return fileService.completeMultipartUpload(completeMultipartReq.getUploadId(),completeMultipartReq.getFileName(),completeMultipartReq.getPartETags());
    }


    @PostMapping(value = "/sampleUpload",consumes = MediaType.MULTIPART_FORM_DATA)
    public ResponseEntity sampleUpload(@RequestPart(name = "file") MultipartFile multipartFile) throws Exception {
        return fileService.sampleUpload(multipartFile);
    }


    @PostMapping(value = "/createBucket")
    public ResponseEntity createBucket(@RequestBody CreateBucketVO createBucketReq) throws Exception {
        return fileService.createBucket(createBucketReq);
    }
}
