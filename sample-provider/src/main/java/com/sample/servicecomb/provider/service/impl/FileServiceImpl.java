package com.sample.servicecomb.provider.service.impl;

import com.obs.services.exception.ObsException;
import com.obs.services.model.CompleteMultipartUploadResult;
import com.obs.services.model.PartEtag;
import com.obs.services.model.PutObjectResult;
import com.obs.services.model.UploadPartResult;
import com.sample.servicecomb.common.bean.ResponseEntity;
import com.sample.servicecomb.common.util.ResponseEntityUtil;
import com.sample.servicecomb.provider.configuration.OBSConfiguration;
import com.sample.servicecomb.provider.service.FileService;
import com.sample.servicecomb.provider.utils.ConstantsUtil;
import com.sample.servicecomb.provider.utils.OBSUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity sampleUpload(MultipartFile file) throws Exception {
        OBSUtil.getInstance(obsConfiguration);
        PutObjectResult result = OBSUtil.putObject(ConstantsUtil.OBS.BUCKET_NAME,file.getOriginalFilename(),file.getInputStream());
        return ResponseEntityUtil.response("ok","0000",result);
    }
}
