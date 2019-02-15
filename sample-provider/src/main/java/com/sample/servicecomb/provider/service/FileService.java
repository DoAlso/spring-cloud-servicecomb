package com.sample.servicecomb.provider.service;

import com.obs.services.model.PartEtag;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FileService {

    /**
     * 大文件上传
     * @param file
     */
    Map<String, Object> upload(MultipartFile file, Integer partNum, String uploadId, List<PartEtag> partEtags) throws Exception;


    /**
     * 文件批量上传
     * @param files
     */
    List<String> uploadFile(MultipartFile... files) throws Exception;
}
