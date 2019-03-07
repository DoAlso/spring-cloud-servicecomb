package com.sample.servicecomb.common.frs;

import com.huaweicloud.frs.client.param.AddExternalFields;
import com.huaweicloud.frs.client.param.AuthInfo;
import com.huaweicloud.frs.client.param.FieldType;
import com.huaweicloud.frs.client.result.*;
import com.huaweicloud.frs.client.service.FrsClient;
import com.sample.servicecomb.common.configuration.FrsConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName FrsClientUtil
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/3/7 14:51
 */
@Component
public class FrsClientBuilder {
    private FrsClient frsClient;

    @Autowired
    private FrsConfigurationProperties properties;

    public FrsClient bulid(){
        if (frsClient == null) {
            synchronized (FrsClientBuilder.class) {
                if (frsClient == null) {
                    frsClient = createFrsClient();
                }
            }
        }
        return frsClient;
    }

    /**
     * 创建人脸库
     * @param faceSetName
     * @param faceSetCapacity
     * @return
     * @throws Exception
     */
    public CreateFaceSetResult createFaceSet(String faceSetName, int faceSetCapacity) throws Exception{
        CreateFaceSetResult createFaceSetResult = frsClient.getFaceSetService().createFaceSet(faceSetName, faceSetCapacity);
        return createFaceSetResult;
    }


    /**
     * 删除人脸库
     * @param faceSetName
     * @return
     * @throws Exception
     */
    public DeleteFaceSetResult deleteFaceSet(String faceSetName) throws Exception {
        DeleteFaceSetResult result = frsClient.getFaceSetService().deleteFaceSet(faceSetName);
        return result;
    }

    /**
     * 人脸入人脸库
     * @param faceSetName
     * @param obsUrl
     * @param map
     * @return
     * @throws Exception
     */
    public AddFaceResult addFaceToSet(String faceSetName, String obsUrl, Map<String, FieldType> map) throws Exception{
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
    public CompareFaceResult compareFace(String sourceFace, String disFace) throws Exception{
        CompareFaceResult result = frsClient.getCompareService().compareFaceByObsUrl(sourceFace, disFace);
        return result;
    }


    /**
     *
     * @param faceSetName
     * @param obsUrl
     * @return
     */
    public SearchFaceResult searchFace(String faceSetName, String obsUrl) throws Exception{
        SearchFaceResult result = frsClient.getSearchService().searchFaceByObsUrl(faceSetName, obsUrl);
        return result;
    }

    /**
     *  人脸检测
     * @param obsUrl
     * @param attr
     * @return
     * @throws Exception
     */
    public DetectFaceResult detectFace(String obsUrl,String attr) throws Exception {
        DetectFaceResult result = frsClient.getDetectService().detectFaceByObsUrl(obsUrl,attr);
        return result;
    }

    private FrsClient createFrsClient(){
        AuthInfo authInfo = new AuthInfo(properties.getEndPoint(),
                properties.getRegion(),
                properties.getAccessKey(),
                properties.getSecretKey());
        FrsClient frsClient = new FrsClient(authInfo,properties.getProjectId());
        return frsClient;
    }
}
