package com.sample.servicecomb.provider.service.impl;

import com.obs.services.model.*;
import com.sample.servicecomb.api.common.ResponseEntity;
import com.sample.servicecomb.api.common.ResponseEntityUtil;
import com.sample.servicecomb.common.obs.ObsClientUtil;
import com.sample.servicecomb.common.util.CommonUtil;
import com.sample.servicecomb.common.util.FileTools;
import com.sample.servicecomb.provider.dao.BaseObsMapper;
import com.sample.servicecomb.provider.model.BaseObs;
import com.sample.servicecomb.provider.model.vo.CreateBucketVO;
import com.sample.servicecomb.provider.service.FileService;
import com.sample.servicecomb.provider.utils.ConstantsUtil;
import org.apache.servicecomb.foundation.common.part.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @ClassName FileServiceImpl
 * @Description TODO
 * @Author 胡亚曦
 * @DATE 2019/2/14 16:19
 */
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private ObsClientUtil obsClientBuilder;
    @Resource
    private BaseObsMapper baseObsMapper;

    @Override
    public ResponseEntity claimUploadId(String fileName) throws Exception {
        String claimUploadId = obsClientBuilder.claimUploadId(ConstantsUtil.OBS.BUCKET_NAME, fileName);
        return ResponseEntityUtil.response("ok", "0000", claimUploadId);
    }

    @Override
    public ResponseEntity upload(MultipartFile multipartFile, String fileName, Integer partNum, String uploadId)
            throws Exception {
        PartEtag partEtag = obsClientBuilder.uploadPart(multipartFile, ConstantsUtil.OBS.BUCKET_NAME, fileName, partNum,
                uploadId);
        return ResponseEntityUtil.response("ok", "0000", partEtag);
    }

    @Override
    public ResponseEntity completeMultipartUpload(String uploadId, String objectKey, List<PartEtag> partETags)
            throws Exception {
        CompleteMultipartUploadResult uploadResult = obsClientBuilder.completeMultipartUpload(uploadId,
                ConstantsUtil.OBS.BUCKET_NAME, objectKey, partETags);
        return ResponseEntityUtil.response("ok", "0000", uploadResult);
    }

    @Override
    public ResponseEntity sampleUpload(MultipartFile file) throws Exception {
        String fileName = "faces/2/" + file.getOriginalFilename();
        PutObjectResult result = obsClientBuilder.putObject(ConstantsUtil.OBS.BUCKET_NAME, fileName,
                file.getInputStream());
        BaseObs baseObs = new BaseObs();
        baseObs.setBucketName(result.getBucketName());
        baseObs.setObjectKey(result.getObjectKey());
        baseObs.setFileName(file.getOriginalFilename());
        baseObs.setFileExtName(result.getObjectKey().substring(result.getObjectKey().lastIndexOf(".") + 1));
        baseObs.setCreater("admin");
        baseObs.setCreated(new Date(System.currentTimeMillis()));
        baseObs.setObsDomain("");
        baseObs.setFileSize(file.getSize());
        baseObs.setEnable(true);
        baseObs.setObjectUrl(result.getObjectUrl());
        baseObs.setVersionId(result.getVersionId());
        baseObs.setEtag(result.getEtag());
        baseObsMapper.insertSelective(baseObs);
        return ResponseEntityUtil.response("ok", "0000", baseObs);
    }

    @Override
    public ResponseEntity createBucket(CreateBucketVO createBucketReq) throws Exception {
        ObsBucket bucket = obsClientBuilder.createBucket(createBucketReq.getBucketName());
        return ResponseEntityUtil.response("ok", "0000", bucket);
    }

    @Override
    public FilePart download(String bucketName, Map<String, String> map) throws Exception {
        String temPath = Files.createTempDirectory("temp").toFile().getAbsolutePath() + "\\";
        String path = temPath + CommonUtil.generateRandomFilePath();
        map.forEach((key, value) -> obsClientBuilder.downloadObject(bucketName, value, path + key));
        String zipFile = temPath + CommonUtil.generateRandomFilename("zip");
        FileTools.zipMultiFile(path, zipFile, false);
        return new FilePart("", zipFile).setDeleteAfterFinished(true);
    }

    @Override
    public FilePart func(String bucketName, Map<String, String> map) {
        try {
            String temPath = Files.createTempDirectory("temp").toFile().getAbsolutePath() + "\\";
            String zipFile = temPath + CommonUtil.generateRandomFilename("zip");
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            map.forEach((key, value) -> {
                ObsObject obsObject = obsClientBuilder.getObsObject(bucketName, value);
                byte[] buf = new byte[1024];
                InputStream inputStream = obsObject.getObjectContent();
                try {
                    zipOut.putNextEntry(new ZipEntry(key));
                    int len;
                    while ((len = inputStream.read(buf)) != -1) {
                        zipOut.write(buf, 0, len);
                    }
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            zipOut.close();
            return new FilePart("", zipFile).setDeleteAfterFinished(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
