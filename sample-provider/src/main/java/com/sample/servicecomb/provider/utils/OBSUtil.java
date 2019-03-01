package com.sample.servicecomb.provider.utils;

import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.exception.ObsException;
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
     * @param bucketName 桶名称
     * @param acl 桶的访问权限
     * @param classEnum 设置桶的存储类别（标准存储，低频访问存储，归档存储）
     * @return
     */
    public static ObsBucket createBucket(String bucketName,AccessControlList acl,StorageClassEnum classEnum){
        ObsBucket obsBucket = new ObsBucket();
        obsBucket.setBucketName(bucketName);
        obsBucket.setAcl(acl);
        obsBucket.setBucketStorageClass(classEnum);
        return obsClient.createBucket(obsBucket);
    }


    /**
     * 创建标准存储模式的桶
     * @param bucketName
     * @param acl
     * @return
     */
    public static ObsBucket createBucket(String bucketName,AccessControlList acl){
        ObsBucket obsBucket = new ObsBucket();
        obsBucket.setBucketName(bucketName);
        obsBucket.setAcl(acl);
        obsBucket.setBucketStorageClass(StorageClassEnum.STANDARD);
        return obsClient.createBucket(obsBucket);
    }


    /**
     * 创建公共读的桶
     * @param bucketName
     * @return
     */
    public static ObsBucket createBucket(String bucketName){
        ObsBucket obsBucket = new ObsBucket();
        obsBucket.setBucketName(bucketName);
        obsBucket.setAcl(AccessControlList.REST_CANNED_PUBLIC_READ);
        obsBucket.setBucketStorageClass(StorageClassEnum.STANDARD);
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
     * @return
     */
    public static String claimUploadId(String bucketName,String fileName) throws Exception{
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, fileName);
        InitiateMultipartUploadResult result = obsClient.initiateMultipartUpload(request);
        return result.getUploadId();
    }


    public static PartEtag uploadPart(MultipartFile multipartFile,String bucketName,String fileName,Integer partNum, String uploadId) throws Exception{
        UploadPartResult uploadPartResult = obsClient.uploadPart(bucketName,fileName,uploadId,partNum+1,multipartFile.getInputStream());
        return new PartEtag(uploadPartResult.getEtag(), uploadPartResult.getPartNumber());
    }

    private static void listAllParts(String bucketName,String objectKey,String uploadId) throws ObsException {
        ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, objectKey, uploadId);
        obsClient.listParts(listPartsRequest);
    }


    /**
     * 合并分段
     * @param uploadId
     * @param bucketName
     * @param objectKey
     */
    public static CompleteMultipartUploadResult completeMultipartUpload(String uploadId, String bucketName, String objectKey,List<PartEtag> partETags) {
        listAllParts(bucketName, objectKey, uploadId);
        Collections.sort(partETags,Comparator.comparingInt(PartEtag::getPartNumber));
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest(bucketName, objectKey, uploadId, partETags);
        return obsClient.completeMultipartUpload(completeMultipartUploadRequest);
    }


    private static ObsClient createObsClient(OBSConfiguration obsConfiguration){
        ObsConfiguration config = new ObsConfiguration();
        config.setEndPoint(obsConfiguration.getEndPoint());
        config.setSocketTimeout(obsConfiguration.getSocketTimeout());
        config.setConnectionTimeout(obsConfiguration.getConnectionTimeout());
        return new ObsClient(obsConfiguration.getAk(),obsConfiguration.getSk(), config);
    }
}
