package com.sample.servicecomb.common.frs;

import com.huaweicloud.frs.client.param.AddExternalFields;
import com.huaweicloud.frs.client.param.AuthInfo;
import com.huaweicloud.frs.client.param.FieldType;
import com.huaweicloud.frs.client.result.*;
import com.huaweicloud.frs.client.service.FrsClient;
import com.sample.servicecomb.common.configuration.FrsConfigurationProperties;
import com.sample.servicecomb.common.util.hw.HuaWeiHttpClient;
import com.sample.servicecomb.common.util.hw.HwApiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * @ClassName FrsClientUtil
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/3/7 14:51
 */
public class FrsClientUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(FrsClientUtil.class);
    private FrsClient frsClient;
    private FrsConfigurationProperties frsProperties;
    public FrsClient getInstance() throws Exception{
        if (frsClient == null) {
            synchronized (FrsClientUtil.class) {
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

    /**
     * 人脸标签服务
     * 添加正脸/辅助人脸标签
     * @return
     * @throws Exception
     */
    public String addFaceLabel(String jsonParam) {
        LOGGER.info("Add Face Label Url:{} jsonParam:{}", HwApiUtil.getFaceUrl(frsProperties, HttpMethod.POST,null,"memberfaces"),jsonParam);
        String result = HuaWeiHttpClient.post(frsProperties.getServiceName(),
                frsProperties.getRegion(),frsProperties.getAccessKey(),
                frsProperties.getSecretKey(),
                HwApiUtil.getFaceUrl(frsProperties, HttpMethod.POST,null,"memberfaces"),
                jsonParam);
        LOGGER.info("Add Face Label Result:{}",result);
        return result;
    }


    /**
     * 人脸标签服务
     * 删除人脸标签
     * @param urlParam
     * @return
     */
    public String deleteFaceLabel(Map<String, String> urlParam){
        LOGGER.info("Delete Face Label Url:{}",HwApiUtil.getFaceUrl(frsProperties, HttpMethod.DELETE,urlParam,"memberfaces"));
        String result = HuaWeiHttpClient.delete(frsProperties.getServiceName(),
                frsProperties.getRegion(), frsProperties.getAccessKey(),
                frsProperties.getSecretKey(),
                HwApiUtil.getFaceUrl(frsProperties, HttpMethod.DELETE, urlParam, "memberfaces"));
        LOGGER.info("Delete Label Result:{}",result);
        return result;
    }

    /**
     * 人脸标签服务
     * 获取人形列表
     * @param jsonParam
     * @return
     */
    public String getRenXinSet(String jsonParam){
        String result = HuaWeiHttpClient.post(frsProperties.getServiceName(),
                frsProperties.getRegion(),frsProperties.getAccessKey(),
                frsProperties.getSecretKey(),
                HwApiUtil.getFaceUrl(frsProperties, HttpMethod.POST,null,"figure-set-content/filter"),
                jsonParam);
        LOGGER.info("GetRenXinSet Result:{}",result);
        return result;
    }


    /**
     * 人脸标签服务
     * 人脸聚合接口
     * @param jsonParam
     * @return
     */
    public String faceAggregation(String jsonParam){
        String result = HuaWeiHttpClient.post(frsProperties.getServiceName(),
                frsProperties.getRegion(),frsProperties.getAccessKey(),
                frsProperties.getSecretKey(),
                HwApiUtil.getFaceUrl(frsProperties, HttpMethod.POST,null,"facepictures"),
                jsonParam);
        LOGGER.info("FaceAggregation Result:{}",result);
        return result;
    }

    /**
     * 人脸标签服务
     * 设置人脸数据推送地址
     * @param jsonParam
     * @return
     */
    public String setFaceCallback(String jsonParam) {
        String result = HuaWeiHttpClient.post(frsProperties.getServiceName(),
                frsProperties.getRegion(),frsProperties.getAccessKey(),
                frsProperties.getSecretKey(),
                HwApiUtil.getFaceUrl(frsProperties, HttpMethod.POST,null,"service_config"),
                jsonParam);
        LOGGER.info("FaceAggregation Result:{}",result);
        return result;
    }

    /**
     * 人脸标签服务
     * 查询所有的陌生人脸标签
     * @param jsonParam
     * @return
     */
    public String searchAllStrangerFaceLabel(String jsonParam) {
        LOGGER.info("searchAllStrangerFaceLabel Url:{},jsonParam:{}",HwApiUtil.getFaceUrl(frsProperties,HttpMethod.POST,null,"face-label-id/stranger-filter"),jsonParam);
        String result = HuaWeiHttpClient.post(frsProperties.getServiceName(),
                frsProperties.getRegion(),frsProperties.getAccessKey(),
                frsProperties.getSecretKey(),
                HwApiUtil.getFaceUrl(frsProperties, HttpMethod.POST,null,"face-label-id/stranger-filter"),
                jsonParam);
        LOGGER.info("searchAllStrangerFaceLabel Result:{}",result);
        return result;
    }


    /**
     * 人脸标签服务
     * 查询所有的成员人脸标签
     * @param jsonParam
     * @return
     */
    public String searchAllMemberFaceLabel(String jsonParam) {
        LOGGER.info("searchAllMemberFaceLabel Url:{},jsonParam:{}",HwApiUtil.getFaceUrl(frsProperties,HttpMethod.POST,null,"face-label-id/member-filter"),jsonParam);
        String result = HuaWeiHttpClient.post(frsProperties.getServiceName(),
                frsProperties.getRegion(),frsProperties.getAccessKey(),
                frsProperties.getSecretKey(),
                HwApiUtil.getFaceUrl(frsProperties, HttpMethod.POST,null,"face-label-id/member-filter"),
                jsonParam);
        LOGGER.info("searchAllMemberFaceLabel Result:{}",result);
        return result;
    }


    private FrsClient createFrsClient() throws Exception{
        AuthInfo authInfo = new AuthInfo(frsProperties.getEndPoint(),
                frsProperties.getRegion(),
                frsProperties.getAccessKey(),
                frsProperties.getSecretKey());
        FrsClient frsClient = new FrsClient(authInfo,frsProperties.getProjectId());
        return frsClient;
    }

    public void setFrsProperties(FrsConfigurationProperties frsProperties) {
        this.frsProperties = frsProperties;
    }
}
