package com.sample.servicecomb.provider.service.impl;

import com.huaweicloud.dis.iface.data.response.Record;
import com.huaweicloud.frs.client.result.*;
import com.huaweicloud.frs.client.result.common.ComplexFace;
import com.obs.services.model.PutObjectResult;
import com.sample.servicecomb.common.bean.FaceCaptured;
import com.sample.servicecomb.common.bean.ResponseEntity;
import com.sample.servicecomb.common.util.FastJsonUtil;
import com.sample.servicecomb.common.util.ImageBase64Util;
import com.sample.servicecomb.common.util.ResponseEntityUtil;
import com.sample.servicecomb.provider.configuration.FrsConfiguration;
import com.sample.servicecomb.provider.configuration.OBSConfiguration;
import com.sample.servicecomb.provider.dao.BaseObsMapper;
import com.sample.servicecomb.provider.dao.FaceHisMapper;
import com.sample.servicecomb.provider.dao.FaceInfoMapper;
import com.sample.servicecomb.provider.dao.FaceSetMapper;
import com.sample.servicecomb.provider.model.FaceHis;
import com.sample.servicecomb.provider.model.FaceInfo;
import com.sample.servicecomb.provider.model.FaceInfoExample;
import com.sample.servicecomb.provider.model.FaceSet;
import com.sample.servicecomb.provider.model.req.CreateFaceReq;
import com.sample.servicecomb.provider.model.req.CreateFaceSetReq;
import com.sample.servicecomb.provider.service.FaceService;
import com.sample.servicecomb.provider.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName FaceServiceImpl
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/2/26 15:21
 */
@Service
public class FaceServiceImpl implements FaceService {
    @Resource
    private FaceHisMapper faceHisMapper;
    @Resource
    private FaceInfoMapper faceInfoMapper;
    @Resource
    private FaceSetMapper faceSetMapper;
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
        if(StringUtils.isNotBlank(faceCaptured.getFace_id())){
            //端测小图拿来进行人脸识别
            FrsUtil.getInstance(frsConfiguration);
            PutObjectResult objectResult = faceUploadOBS(faceCaptured.getCamera_id(),objectKey,fileName,"jpg",faceCaptured.getImage_data());
            DetectFaceResult detectFaceResult = FrsUtil.detectFace("/"+objectResult.getBucketName()+"/"+objectResult.getObjectKey(),"1,2,4,5");
            AddFaceResult addFaceResult = FrsUtil.addFaceToSet("hoolink-face-his","/"+objectResult.getBucketName()+"/"+objectResult.getObjectKey(),null);
            FaceHis faceHis = new FaceHis();
            faceHis.setFaceLabelId(addFaceResult.getFaces().get(0).getFaceId());
            faceHis.setOriImgId(faceCaptured.getOri_img_id());
            faceHis.setCameraId(faceCaptured.getCamera_id());
            faceHis.setFaceSetId(addFaceResult.getFaceSetId());
            faceHis.setSmallObsUrl(objectResult.getObjectUrl());
            faceHis.setFaceDate(new Date(System.currentTimeMillis()));
            faceHis.setAge(detectFaceResult.getFaces().get(0).getAttributes().getAge());
            faceHis.setGender(detectFaceResult.getFaces().get(0).getAttributes().getGender());
            faceHis.setSmile(detectFaceResult.getFaces().get(0).getAttributes().getSmile());
            faceHis.setHat(detectFaceResult.getFaces().get(0).getAttributes().getDress().getHat());
            faceHis.setGlass(detectFaceResult.getFaces().get(0).getAttributes().getDress().getGlass());
            SearchFaceResult result = FrsUtil.searchFace("hoolink-face","/"+objectResult.getBucketName()+"/"+objectResult.getObjectKey());
            List<ComplexFace> faces = result.getFaces();
            for(ComplexFace face:faces){
                if(face.getSimilarity() > 0.9){
                    faceHis.setSimilarity(face.getSimilarity());
                    FaceInfoExample example = new FaceInfoExample();
                    FaceInfoExample.Criteria criteria = example.createCriteria();
                    criteria.andFaceLabelIdEqualTo(face.getFaceId());
                    FaceInfo faceInfo = faceInfoMapper.selectByExample(example).get(0);
                    faceHis.setOriFaceLabelId(face.getFaceId());
                    faceHis.setOriginalObsUrl(faceInfo.getObsUrl());
                    faceHis.setName(faceInfo.getName());
                    break;
                }
            }
            faceHisMapper.insertSelective(faceHis);
        }
    }

    @Override
    public ResponseEntity createVipFace(CreateFaceReq createFaceReq) throws Exception {
        FrsUtil.getInstance(frsConfiguration);
        AddFaceResult result = FrsUtil.addFaceToSet(createFaceReq.getFaceSetName(),createFaceReq.getObsUrl(),null);
        FaceInfo faceInfo = createFaceReq.getFaceInfo();
        faceInfo.setFaceLabelId(result.getFaces().get(0).getFaceId());
        faceInfoMapper.insertSelective(faceInfo);
        return ResponseEntityUtil.response("ok","000000",null);
    }

    @Override
    public ResponseEntity createFaceSet(CreateFaceSetReq createFaceSetReq) throws Exception {
        FrsUtil.getInstance(frsConfiguration);
        CreateFaceSetResult result = FrsUtil.createFaceSet(createFaceSetReq.getFaceSetName(),createFaceSetReq.getFaceSetCapacity());
        FaceSet faceSet = new FaceSet();
        faceSet.setFaceSetName(result.getFaceSetInfo().getFaceSetName());
        faceSet.setFaceSetId(result.getFaceSetInfo().getFaceSetId());
        faceSet.setFaceSetCapacity(Long.valueOf(result.getFaceSetInfo().getFaceSetCapacity()));
        faceSetMapper.insertSelective(faceSet);
        return ResponseEntityUtil.response("ok","000000",result);
    }


    @Override
    public ResponseEntity deleteFaceSet(String faceSetName) throws Exception {
        FrsUtil.getInstance(frsConfiguration);
        DeleteFaceSetResult result = FrsUtil.deleteFaceSet(faceSetName);
        return ResponseEntityUtil.response("ok","000000",result);
    }

    private PutObjectResult faceUploadOBS(String deviceId, String objectKey, String fileName, String extName, String base64) throws Exception {
        OBSUtil.getInstance(obsConfiguration);
        MultipartFile multipartFile = ImageBase64Util.builder().base64ToMultipart(base64);
        PutObjectResult result = OBSUtil.putObject(ConstantsUtil.OBS.BUCKET_NAME, objectKey,multipartFile.getInputStream());
        return result;
    }
}
