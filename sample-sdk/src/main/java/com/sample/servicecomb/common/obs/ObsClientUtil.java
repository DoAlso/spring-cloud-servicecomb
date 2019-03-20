package com.sample.servicecomb.common.obs;

import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.exception.ObsException;
import com.obs.services.model.*;
import com.sample.servicecomb.common.configuration.ObsConfigurationProperties;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @ClassName ObsClientBuilder
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/3/7 15:14
 */
public class ObsClientUtil {
    public final Integer HTTP_OK = 200;
    private ObsClient obsClient;
    private ObsConfigurationProperties ObsProperties;
    public ObsClient getInstance(){
        if (obsClient == null) {
            synchronized (ObsClientUtil.class) {
                if (obsClient == null) {
                    obsClient = createObsClient();
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
    public ObsBucket createBucket(String bucketName, AccessControlList acl, StorageClassEnum classEnum){
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
    public ObsBucket createBucket(String bucketName,AccessControlList acl){
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
    public ObsBucket createBucket(String bucketName){
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
    public PutObjectResult putObject(String bucketName, String objectKey, File file, ObjectMetadata metadata) throws Exception {
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
    public PutObjectResult putObject(String bucketName, String objectKey, File file) throws Exception {
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
    public PutObjectResult putObject(String bucketName, String objectKey, InputStream inputStream, ObjectMetadata metadata) throws Exception {
        return obsClient.putObject(bucketName, objectKey, inputStream, metadata);
    }

    /**
     * @param bucketName
     * @param objectKey
     * @param inputStream
     * @return
     * @throws Exception
     */
    public PutObjectResult putObject(String bucketName, String objectKey, InputStream inputStream) throws Exception {
        return obsClient.putObject(bucketName, objectKey, inputStream);
    }

    /**
     * obs文件下载
     * @param bucketName
     * @param objectKey
     * @return
     */
    public DownloadFileResult downloadObject(String bucketName,String objectKey,String fileName){
        return obsClient.downloadFile(new DownloadFileRequest(bucketName,objectKey,fileName));
    }

    /**
     * 获取Obs对象信息
     * @param bucketName
     * @param objectKey
     * @return
     */
    public ObsObject getObsObject(String bucketName,String objectKey){
        return obsClient.getObject(bucketName, objectKey);
    }


    /**
     * 删除存储对象的桶
     * @param bucketName
     */
    public boolean deleteBucket(String bucketName){
        HeaderResponse response = obsClient.deleteBucket(bucketName);
        if(response.getStatusCode() == HTTP_OK){
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
    public String claimUploadId(String bucketName,String fileName) throws Exception{
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, fileName);
        InitiateMultipartUploadResult result = obsClient.initiateMultipartUpload(request);
        return result.getUploadId();
    }


    public PartEtag uploadPart(MultipartFile multipartFile, String bucketName, String fileName, Integer partNum, String uploadId) throws Exception{
        UploadPartResult uploadPartResult = obsClient.uploadPart(bucketName,fileName,uploadId,partNum+1,multipartFile.getInputStream());
        return new PartEtag(uploadPartResult.getEtag(), uploadPartResult.getPartNumber());
    }

    private void listAllParts(String bucketName,String objectKey,String uploadId) throws ObsException {
        ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, objectKey, uploadId);
        obsClient.listParts(listPartsRequest);
    }


    /**
     * 合并分段
     * @param uploadId
     * @param bucketName
     * @param objectKey
     */
    public CompleteMultipartUploadResult completeMultipartUpload(String uploadId, String bucketName, String objectKey, List<PartEtag> partETags) {
        listAllParts(bucketName, objectKey, uploadId);
        Collections.sort(partETags, Comparator.comparingInt(PartEtag::getPartNumber));
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest(bucketName, objectKey, uploadId, partETags);
        return obsClient.completeMultipartUpload(completeMultipartUploadRequest);
    }


    private ObsClient createObsClient(){
        ObsConfiguration config = new ObsConfiguration();
        config.setEndPoint(ObsProperties.getEndPoint());
        config.setSocketTimeout(ObsProperties.getSocketTimeout());
        config.setConnectionTimeout(ObsProperties.getConnectionTimeout());
        return new ObsClient(ObsProperties.getAk(),ObsProperties.getSk(), config);
    }

    public void setObsProperties(ObsConfigurationProperties obsProperties) {
        ObsProperties = obsProperties;
    }
}
