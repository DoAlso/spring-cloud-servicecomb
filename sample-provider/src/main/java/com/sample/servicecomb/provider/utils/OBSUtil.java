package com.sample.servicecomb.provider.utils;

import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.model.*;
import com.sample.servicecomb.provider.configuration.OBSConfiguration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * @ClassName OBSUtil
 * @Description TODO
 * @Author 胡亚曦
 * @DATE 2019/2/13 9:33
 */
public class OBSUtil {
    private static ObsClient obsClient;

    /**
     * 创建ObsClient客户端实例
     * @return
     */
    public static ObsClient getInstance(OBSConfiguration obsConfiguration){
        if (obsClient == null) {
            synchronized (OBSUtil.class) {
                if (obsClient == null) {
                    obsClient = createObsClient(obsConfiguration);
                }
            }
        }
        return obsClient;
    }


    /**
     * 创建存储对象的桶
     * @param bucketName
     * @return
     */
    public static ObsBucket createBucket(String bucketName){
        ObsBucket obsBucket = new ObsBucket();
        obsBucket.setBucketName(bucketName);
        return obsClient.createBucket(obsBucket);
    }


    /**
     * put请求存储对象
     *
     * @param bucketName
     * @param objectKey
     * @param file
     * @param metadata
     * @return
     * @throws Exception
     */
    public static PutObjectResult putObject(String bucketName, String objectKey, File file, ObjectMetadata metadata) throws Exception {
        return obsClient.putObject(bucketName, objectKey, file, metadata);
    }

    /**
     * put请求存储对象
     *
     * @param bucketName
     * @param objectKey
     * @param file
     * @return
     * @throws Exception
     */
    public static PutObjectResult putObject(String bucketName, String objectKey, File file) throws Exception {
        return obsClient.putObject(bucketName, objectKey, file);
    }

    /**
     * @param bucketName
     * @param objectKey
     * @param inputStream
     * @param metadata
     * @return
     * @throws Exception
     */
    public static PutObjectResult putObject(String bucketName, String objectKey, InputStream inputStream, ObjectMetadata metadata) throws Exception {
        return obsClient.putObject(bucketName, objectKey, inputStream, metadata);
    }

    /**
     * @param bucketName
     * @param objectKey
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static PutObjectResult putObject(String bucketName, String objectKey, InputStream inputStream) throws Exception {
        return obsClient.putObject(bucketName, objectKey, inputStream);
    }


    /**
     * 删除存储对象的桶
     * @param bucketName
     */
    public static boolean deleteBucket(String bucketName){
        HeaderResponse response = obsClient.deleteBucket(bucketName);
        if(response.getStatusCode() == ConstantsUtil.OBS.HTTP_OK){
            return true;
        }
        return false;
    }


    /**
     * 文件分片上传
     * 数据初始化
     * @param bucketName
     * @param objectKey
     * @return
     */
    public static String claimUploadId(String bucketName,String objectKey) {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, objectKey);
        InitiateMultipartUploadResult result = obsClient.initiateMultipartUpload(request);
        return result.getUploadId();
    }


    public static Map<String,Object> uploadPart(String bucketName, String objectKey, Integer partNum, String uploadId, MultipartFile file) throws Exception{
        UploadPartRequest request = new UploadPartRequest(bucketName, objectKey);
        // 设置Upload ID
        request.setUploadId(uploadId);
        // 设置分段号，范围是1~10000，
        request.setPartNumber(1);
        // 设置将要上传的大文件流
        request.setInput(file.getInputStream());
        // 设置第二段的段偏移量
        request.setOffset(partNum*ConstantsUtil.OBS.FILE_PART);
        // 设置分段大小
        request.setPartSize(ConstantsUtil.OBS.FILE_PART);
        UploadPartResult result = obsClient.uploadPart(request);
        Map<String,Object> uploadResult = new HashMap<>(2);
        uploadResult.put("partEtag",new PartEtag(result.getEtag(),result.getPartNumber()));
        uploadResult.put("uploadId",uploadId);
        return uploadResult;
    }


    /**
     * 合并分段
     * @param uploadId
     * @param bucketName
     * @param objectKey
     * @param partEtags
     */
    public static void completeMultipartUpload(String uploadId, String bucketName, String objectKey, List<PartEtag> partEtags) {
        Collections.sort(partEtags, Comparator.comparingInt(PartEtag::getPartNumber));
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest(bucketName, objectKey, uploadId, partEtags);
        obsClient.completeMultipartUpload(completeMultipartUploadRequest);
    }


    private static ObsClient createObsClient(OBSConfiguration obsConfiguration){
        ObsConfiguration config = new ObsConfiguration();
        config.setEndPoint(obsConfiguration.getEndPoint());
        config.setSocketTimeout(obsConfiguration.getSocketTimeout());
        config.setConnectionTimeout(obsConfiguration.getConnectionTimeout());
        return new ObsClient(obsConfiguration.getAk(),obsConfiguration.getSk(), config);
    }
}
