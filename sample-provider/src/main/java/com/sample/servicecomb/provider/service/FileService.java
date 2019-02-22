package com.sample.servicecomb.provider.service;
import com.obs.services.model.PartEtag;
import com.sample.servicecomb.common.bean.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface FileService {

    /**
     * 获取文件上传的ID
     * @param fileName
     * @return
     * @throws Exception
     */

    ResponseEntity claimUploadId(String fileName) throws Exception;


    /**
     * 大文件分片上传
     * @param partNum
     * @param uploadId
     * @return
     * @throws Exception
     */
    ResponseEntity upload(MultipartFile multipartFile,String fileName,Integer partNum, String uploadId) throws Exception;


    /**
     * 文件合并
     * @param uploadId
     * @param objectKey
     * @param partETags
     * @return
     * @throws Exception
     */
    ResponseEntity completeMultipartUpload(String uploadId, String objectKey, List<PartEtag> partETags) throws Exception;

    /**
     * 但文件上传
     * @param file
     * @return
     * @throws Exception
     */
    ResponseEntity sampleUpload(MultipartFile file) throws Exception;
}
