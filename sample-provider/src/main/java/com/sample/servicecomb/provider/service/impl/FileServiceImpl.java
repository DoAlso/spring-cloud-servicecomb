package com.sample.servicecomb.provider.service.impl;

import com.obs.services.model.*;
import com.sample.servicecomb.api.common.ResponseEntity;
import com.sample.servicecomb.api.common.ResponseEntityUtil;
import com.sample.servicecomb.common.obs.ObsClientUtil;
import com.sample.servicecomb.provider.dao.BaseObsMapper;
import com.sample.servicecomb.provider.model.BaseObs;
import com.sample.servicecomb.provider.model.vo.CreateBucketVO;
import com.sample.servicecomb.provider.service.FileService;
import com.sample.servicecomb.provider.utils.ConstantsUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
        String claimUploadId = obsClientBuilder.claimUploadId(ConstantsUtil.OBS.BUCKET_NAME,fileName);
        return ResponseEntityUtil.response("ok","0000",claimUploadId);
    }

    @Override
    public ResponseEntity upload(MultipartFile multipartFile,String fileName,Integer partNum, String uploadId) throws Exception{
        PartEtag partEtag = obsClientBuilder.uploadPart(multipartFile,ConstantsUtil.OBS.BUCKET_NAME,fileName,partNum,uploadId);
        return ResponseEntityUtil.response("ok","0000",partEtag);
    }

    @Override
    public ResponseEntity completeMultipartUpload(String uploadId, String objectKey, List<PartEtag> partETags) throws Exception {
        CompleteMultipartUploadResult uploadResult = obsClientBuilder.completeMultipartUpload(uploadId,ConstantsUtil.OBS.BUCKET_NAME,objectKey,partETags);
        return ResponseEntityUtil.response("ok","0000",uploadResult);
    }

    @Override
    public ResponseEntity sampleUpload(MultipartFile file) throws Exception {
        PutObjectResult result = obsClientBuilder.putObject(ConstantsUtil.OBS.BUCKET_NAME,file.getOriginalFilename(),file.getInputStream());
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
        return ResponseEntityUtil.response("ok","0000",baseObs);
    }

    @Override
    public ResponseEntity createBucket(CreateBucketVO createBucketReq) throws Exception {
        ObsBucket bucket = obsClientBuilder.createBucket(createBucketReq.getBucketName());
        return ResponseEntityUtil.response("ok","0000",bucket);
    }
}
