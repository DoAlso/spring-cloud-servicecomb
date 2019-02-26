package com.sample.servicecomb.provider.service.impl;

import com.obs.services.model.*;
import com.sample.servicecomb.common.bean.ResponseEntity;
import com.sample.servicecomb.common.util.ResponseEntityUtil;
import com.sample.servicecomb.provider.configuration.OBSConfiguration;
import com.sample.servicecomb.provider.dao.BaseObsMapper;
import com.sample.servicecomb.provider.dao.FaceSetMapper;
import com.sample.servicecomb.provider.model.BaseObs;
import com.sample.servicecomb.provider.model.FaceSet;
import com.sample.servicecomb.provider.model.req.CreateBucketReq;
import com.sample.servicecomb.provider.service.FileService;
import com.sample.servicecomb.provider.utils.ConstantsUtil;
import com.sample.servicecomb.provider.utils.OBSUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private OBSConfiguration obsConfiguration;
    @Resource
    private BaseObsMapper baseObsMapper;
    @Resource
    private FaceSetMapper faceSetMapper;

    @Override
    public ResponseEntity claimUploadId(String fileName) throws Exception {
        OBSUtil.getInstance(obsConfiguration);
        String claimUploadId = OBSUtil.claimUploadId(ConstantsUtil.OBS.BUCKET_NAME,fileName);
        return ResponseEntityUtil.response("ok","0000",claimUploadId);
    }

    @Override
    public ResponseEntity upload(MultipartFile multipartFile,String fileName,Integer partNum, String uploadId) throws Exception{
        OBSUtil.getInstance(obsConfiguration);
        PartEtag partEtag = OBSUtil.uploadPart(multipartFile,ConstantsUtil.OBS.BUCKET_NAME,fileName,partNum,uploadId);
        return ResponseEntityUtil.response("ok","0000",partEtag);
    }

    @Override
    public ResponseEntity completeMultipartUpload(String uploadId, String objectKey, List<PartEtag> partETags) throws Exception {
        OBSUtil.getInstance(obsConfiguration);
        CompleteMultipartUploadResult uploadResult = OBSUtil.completeMultipartUpload(uploadId,ConstantsUtil.OBS.BUCKET_NAME,objectKey,partETags);
        return ResponseEntityUtil.response("ok","0000",uploadResult);
    }

    @Override
    public ResponseEntity sampleUpload(MultipartFile file,Long faceSetId) throws Exception {
        FaceSet faceSet = faceSetMapper.selectByPrimaryKey(faceSetId);
        OBSUtil.getInstance(obsConfiguration);
        PutObjectResult result = OBSUtil.putObject(faceSet.getFaceSetName(),file.getOriginalFilename(),file.getInputStream());
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
    public ResponseEntity createBucket(CreateBucketReq createBucketReq) throws Exception {
        OBSUtil.getInstance(obsConfiguration);
        ObsBucket bucket = OBSUtil.createBucket(createBucketReq.getBucketName());
        return ResponseEntityUtil.response("ok","0000",bucket);
    }
}
