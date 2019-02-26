package com.sample.servicecomb.provider.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName DISConfiguration
 * @Description TODO
 * @Author Doalso
 * @DATE 2018/11/1 10:16
 */
@ConfigurationProperties(value = "huawei.dis")
public class DISConfiguration {
    private String region;
    private String endPoint;
    private String accessKey;
    private String secretKey;
    private String projectId;

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
}
