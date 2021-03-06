package com.sample.servicecomb.provider.service;
import com.obs.services.model.PartEtag;
import com.sample.servicecomb.api.common.ResponseEntity;
import com.sample.servicecomb.provider.model.vo.CreateBucketVO;
import org.apache.servicecomb.foundation.common.part.FilePart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


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


    /**
     * 创建OBS桶
     * @param createBucketReq
     * @return
     * @throws Exception
     */
    ResponseEntity createBucket(CreateBucketVO createBucketReq) throws Exception;


    /**
     * OBS 文件下载
     * @param bucketName
     * @param map
     * @return
     * @throws Exception
     */
    FilePart download(String bucketName, Map<String,String> map) throws Exception;

    FilePart func(String bucketName, Map<String,String> map);

    /**
     * 上传网络资源
     * @param bucketName
     * @param objectKey
     * @param url
     * @return
     * @throws Exception
     */
    ResponseEntity uploadByUrl(String bucketName,String objectKey,String url) throws Exception;
}
