package com.sample.servicecomb.provider.model.vo;

import java.io.Serializable;

/**
 * @ClassName CreateFaceSetVO
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/2/28 14:09
 */
public class CreateFaceSetVO implements Serializable {
    private String faceSetName;
    private int faceSetCapacity;

    public String getFaceSetName() {
        return faceSetName;
    }

    public void setFaceSetName(String faceSetName) {
        this.faceSetName = faceSetName;
    }

    public int getFaceSetCapacity() {
        return faceSetCapacity;
    }

    public void setFaceSetCapacity(int faceSetCapacity) {
        this.faceSetCapacity = faceSetCapacity;
    }
}
