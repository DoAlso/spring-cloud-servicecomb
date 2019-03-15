package com.sample.servicecomb.provider.model.vo;

import java.util.List;

/**
 * @ClassName AddAssistantFaceVO
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/3/15 17:07
 */
public class AddAssistantFaceVO {
    private String faceSetName;
    private String faceBucketName;
    private List<String> faceObskeys;
    private String faceLabelId;

    public String getFaceSetName() {
        return faceSetName;
    }

    public void setFaceSetName(String faceSetName) {
        this.faceSetName = faceSetName;
    }

    public String getFaceBucketName() {
        return faceBucketName;
    }

    public void setFaceBucketName(String faceBucketName) {
        this.faceBucketName = faceBucketName;
    }

    public List<String> getFaceObskeys() {
        return faceObskeys;
    }

    public void setFaceObskeys(List<String> faceObskeys) {
        this.faceObskeys = faceObskeys;
    }

    public String getFaceLabelId() {
        return faceLabelId;
    }

    public void setFaceLabelId(String faceLabelId) {
        this.faceLabelId = faceLabelId;
    }
}
