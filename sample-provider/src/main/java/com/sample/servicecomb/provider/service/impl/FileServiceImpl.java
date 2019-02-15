package com.sample.servicecomb.provider.service.impl;

import com.obs.services.exception.ObsException;
import com.obs.services.model.PartEtag;
import com.obs.services.model.PutObjectResult;
import com.sample.servicecomb.provider.configuration.OBSConfiguration;
import com.sample.servicecomb.provider.service.FileService;
import com.sample.servicecomb.provider.utils.ConstantsUtil;
import com.sample.servicecomb.provider.utils.OBSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    public Map<String, Object> upload(MultipartFile file, Integer partNum, String uploadId, List<PartEtag> partEtags) throws Exception{
        Map<String,Object> uploadMap = new HashMap<>(2);
        OBSUtil.getInstance(obsConfiguration);
        String objectKey = file.getOriginalFilename();
        if(uploadId == null){
            String claimUploadId = OBSUtil.claimUploadId(ConstantsUtil.OBS.BUCKET_NAME,objectKey);
            uploadMap.put("uploadId",claimUploadId);
            return uploadMap;
        }
        if(partEtags == null || partEtags.isEmpty()){
            return OBSUtil.uploadPart(ConstantsUtil.OBS.BUCKET_NAME,objectKey,partNum,uploadId,file);
        }
        OBSUtil.completeMultipartUpload(uploadId,ConstantsUtil.OBS.BUCKET_NAME,objectKey,partEtags);
        uploadMap.put("errorCode",200);
        uploadMap.put("errorMsg","success");
        return uploadMap;
    }

    @Override
    public List<String> uploadFile(MultipartFile... files) throws Exception{
        OBSUtil.getInstance(obsConfiguration);
        List<String> urls = new ArrayList<>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10,10,60L, TimeUnit.SECONDS,new LinkedBlockingQueue<>(10));
        for (MultipartFile file:files){
            executor.submit(()-> {
                try {
                    PutObjectResult result = OBSUtil.putObject(ConstantsUtil.OBS.BUCKET_NAME,file.getOriginalFilename(),file.getInputStream());
                    urls.add(result.getObjectUrl());
                }catch (Exception e){
                    e.printStackTrace();
                }

            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                executor.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }
}
