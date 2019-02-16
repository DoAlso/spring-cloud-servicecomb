package com.sample.servicecomb.provider.controller;

import com.obs.services.model.PartEtag;
import com.sample.servicecomb.provider.service.FileService;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

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

    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA)
    public Map<String, Object> upload(@RequestPart(name = "file") MultipartFile file, Integer partNum, String uploadId, @RequestBody List<PartEtag> partEtags) throws Exception {
        return fileService.upload(file,partNum,uploadId,partEtags);
    }


    @PostMapping(value = "/uploadFile",consumes = MediaType.MULTIPART_FORM_DATA)
    public List<String> uploadFile(@RequestPart(name = "files") MultipartFile... files) throws Exception{
       return fileService.uploadFile(files);
    }
}
