package com.sample.servicecomb.provider.utils;

import com.huaweicloud.frs.client.param.AddExternalFields;
import com.huaweicloud.frs.client.param.AuthInfo;
import com.huaweicloud.frs.client.param.CreateExternalFields;
import com.huaweicloud.frs.client.param.FieldType;
import com.huaweicloud.frs.client.result.AddFaceResult;
import com.huaweicloud.frs.client.result.CompareFaceResult;
import com.huaweicloud.frs.client.result.CreateFaceSetResult;
import com.huaweicloud.frs.client.result.DetectFaceResult;
import com.huaweicloud.frs.client.service.FrsClient;
import com.sample.servicecomb.provider.configuration.FrsConfiguration;

import java.util.Map;

/**
 * @ClassName FrsUtil
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/2/26 9:41
 */
public class FrsUtil {

    private static FrsClient frsClient;

    public static FrsClient getInstance(FrsConfiguration frsConfiguration){
        if (frsClient == null) {
            synchronized (OBSUtil.class) {
                if (frsClient == null) {
                    frsClient = createFrsClient(frsConfiguration);
                }
            }
        }
        return frsClient;
    }


    /**
     * 创建人脸库
     * @param faceSetName
     * @param faceSetCapacity
     * @param extFields
     * @return
     * @throws Exception
     */
    public static CreateFaceSetResult createFaceSet(String faceSetName, int faceSetCapacity, Map<String, FieldType> extFields) throws Exception{
        CreateExternalFields externalFields = new CreateExternalFields();
        if(extFields != null && !extFields.isEmpty()){
            extFields.forEach((key,value) -> externalFields.addField(key,value));
        }
        CreateFaceSetResult createFaceSetResult = frsClient.getFaceSetService().createFaceSet(faceSetName, faceSetCapacity, externalFields);
        return createFaceSetResult;
    }

    /**
     * 人脸入人脸库
     * @param faceSetName
     * @param obsUrl
     * @param map
     * @return
     * @throws Exception
     */
    public static AddFaceResult addFaceToSet(String faceSetName,String obsUrl,Map<String,FieldType> map) throws Exception{
        AddExternalFields externalFields = new AddExternalFields();
        if(map != null && !map.isEmpty()){
            map.forEach((key,value) -> externalFields.addField(key,value));
        }
        AddFaceResult addFaceResult = frsClient.getFaceService().addFaceByObsUrl(faceSetName,obsUrl,externalFields);
        return addFaceResult;
    }


    /**
     * 人脸对比
     * @param sourceFace
     * @param disFace
     * @return
     * @throws Exception
     */
    public static CompareFaceResult compareFace(String sourceFace,String disFace) throws Exception{
        CompareFaceResult result = frsClient.getCompareService().compareFaceByObsUrl(sourceFace, disFace);
        return result;
    }

    /**
     *  人脸检测
     * @param obsUrl
     * @param attr
     * @return
     * @throws Exception
     */
    public static DetectFaceResult detectFace(String obsUrl,String attr) throws Exception {
        DetectFaceResult result = frsClient.getDetectService().detectFaceByObsUrl(obsUrl,attr);
        return result;
    }

    public static FrsClient createFrsClient(FrsConfiguration frsConfiguration){
        AuthInfo authInfo = new AuthInfo(frsConfiguration.getEndPoint(),
                frsConfiguration.getRegion(),
                frsConfiguration.getAccessKey(),
                frsConfiguration.getSecretKey());
        FrsClient frsClient = new FrsClient(authInfo,frsConfiguration.getProjectId());
        System.out.println(frsConfiguration.getProjectId());
        return frsClient;
    }
}
