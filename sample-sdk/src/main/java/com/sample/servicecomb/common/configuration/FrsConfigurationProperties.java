package com.sample.servicecomb.common.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName FrsConfigurationProperties
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/3/7 14:39
 */
@ConfigurationProperties(prefix = "huawei.frs")
public class FrsConfigurationProperties {
    private String region = "cn-north-1";
    private String endPoint = "https://face.cn-north-1.myhuaweicloud.com";
    private String accessKey = "3TWL7VJQ4IM45X3DAMLF";
    private String secretKey = "i1aCblcgP4ZyoUwBHZnWd4rJCb5skN2Ya0LzSix5";
    private String serviceName = "frs";
    private String projectId = "3c9ff3e423154e03bb8d65cfc5ae0d67";
    private String apiVersion = "v1";

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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }
}
