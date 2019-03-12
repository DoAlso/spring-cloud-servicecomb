package com.sample.servicecomb.provider.model.vo;

import com.sample.servicecomb.provider.model.FaceInfo;

import java.io.Serializable;

/**
 * @ClassName CreateFaceVO
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/2/28 14:21
 */
public class CreateFaceVO implements Serializable {
    private FaceInfo faceInfo;
    private String faceSetId;
    private String faceSetName;
    private Long obsId;
    private String obsUrl;

    public FaceInfo getFaceInfo() {
        return faceInfo;
    }

    public void setFaceInfo(FaceInfo faceInfo) {
        this.faceInfo = faceInfo;
    }

    public String getFaceSetId() {
        return faceSetId;
    }

    public void setFaceSetId(String faceSetId) {
        this.faceSetId = faceSetId;
    }

    public String getFaceSetName() {
        return faceSetName;
    }

    public void setFaceSetName(String faceSetName) {
        this.faceSetName = faceSetName;
    }

    public Long getObsId() {
        return obsId;
    }

    public void setObsId(Long obsId) {
        this.obsId = obsId;
    }

    public String getObsUrl() {
        return obsUrl;
    }

    public void setObsUrl(String obsUrl) {
        this.obsUrl = obsUrl;
    }
}
