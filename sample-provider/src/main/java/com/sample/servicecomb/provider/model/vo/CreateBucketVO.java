package com.sample.servicecomb.provider.model.vo;

import java.io.Serializable;

/**
 * @ClassName CreateBucketVO
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/2/25 17:43
 */
public class CreateBucketVO implements Serializable {
    private Long projectId;
    private String bucketName;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
