package com.sample.servicecomb.common.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName DisConfigurationProperties
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/3/7 15:22
 */
@ConfigurationProperties(value = "huawei.dis")
public class DisConfigurationProperties {
    private String region = "cn-north-1";
    private String endPoint = "";
    private String accessKey = "";
    private String secretKey = "";
    private String projectId = "";
    private String partitionId = "0";

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPartitionId() {
        return partitionId;
    }

    public void setPartitionId(String partitionId) {
        this.partitionId = partitionId;
    }
}
