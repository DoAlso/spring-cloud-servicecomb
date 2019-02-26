package com.sample.servicecomb.provider.service.impl;

import com.huaweicloud.dis.iface.data.response.Record;
import com.huaweicloud.frs.client.result.CompareFaceResult;
import com.obs.services.model.PutObjectResult;
import com.sample.servicecomb.common.bean.FaceCaptured;
import com.sample.servicecomb.common.bean.ResponseEntity;
import com.sample.servicecomb.common.util.FastJsonUtil;
import com.sample.servicecomb.common.util.ResponseEntityUtil;
import com.sample.servicecomb.provider.configuration.FrsConfiguration;
import com.sample.servicecomb.provider.configuration.OBSConfiguration;
import com.sample.servicecomb.provider.dao.BaseObsMapper;
import com.sample.servicecomb.provider.dao.FaceInfoMapper;
import com.sample.servicecomb.provider.model.BaseObs;
import com.sample.servicecomb.provider.model.BaseObsExample;
import com.sample.servicecomb.provider.model.FaceInfo;
import com.sample.servicecomb.provider.service.FaceService;
import com.sample.servicecomb.provider.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.stream.FileImageInputStream;
import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName FaceServiceImpl
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/2/26 15:21
 */
@Service
public class FaceServiceImpl implements FaceService {
    @Resource
    private FaceInfoMapper faceInfoMapper;
    @Resource
    private BaseObsMapper baseObsMapper;
    @Autowired
    private OBSConfiguration obsConfiguration;
    @Autowired
    private FrsConfiguration frsConfiguration;
    @Override
    public void faceCapture(String streamName, Record record) throws Exception {
        FaceCaptured faceCaptured = FastJsonUtil.toBean(new String(record.getData().array()), FaceCaptured.class);
        //拼接文件名
        String path = new StringBuilder()
                .append(faceCaptured.getCamera_id())
                .append(ConstantsUtil.CommonKey.DELIMITER)
                .append(faceCaptured.getTime())
                .append(ConstantsUtil.CommonKey.DELIMITER).toString();
        String fileName = CommonUtil.generateRandomFilename("jpg");
        String objectKey = new StringBuilder(path).append(fileName).toString();
        BaseObsExample example = new BaseObsExample();
        BaseObsExample.Criteria criteria = example.createCriteria();
        criteria.andBucketNameEqualTo(ConstantsUtil.OBS.FACE_SET_BUCKET);
        List<BaseObs> baseObsList = baseObsMapper.selectByExample(example);
        Executor executor =  new ThreadPoolExecutor(1,5,60L, TimeUnit.SECONDS,new LinkedBlockingQueue<>());
        if(StringUtils.isNotBlank(faceCaptured.getFace_id())){
            //端测小图拿来进行人脸识别
            PutObjectResult objectResult = faceUploadOBS(faceCaptured.getCamera_id(),objectKey,fileName,"jpg",faceCaptured.getImage_data());
            baseObsList.forEach(key->{
                executor.execute(()->{
                    FrsUtil.getInstance(frsConfiguration);
                    try {
                        System.out.println(key.getBucketName()+"/"+key.getObjectKey());
                        System.out.println(objectResult.getBucketName()+"/"+objectResult.getObjectKey());
                        CompareFaceResult result = FrsUtil.compareFace(key.getBucketName()+"/"+key.getObjectKey(), objectResult.getBucketName()+"/"+objectResult.getObjectKey());
                        System.out.println(result.getSimilarity());
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                });
            });
        }
    }

    @Override
    public ResponseEntity createVipFace(FaceInfo faceInfo) throws Exception {
        faceInfoMapper.insertSelective(faceInfo);
        return ResponseEntityUtil.response("ok","000000",null);
    }


    private PutObjectResult faceUploadOBS(String deviceId, String objectKey, String fileName, String extName, String base64) throws Exception {
        OBSUtil.getInstance(obsConfiguration);
        PutObjectResult result = OBSUtil.putObject(ConstantsUtil.OBS.BUCKET_NAME, objectKey, new ByteArrayInputStream(base64.getBytes()));
        return result;
    }
}
