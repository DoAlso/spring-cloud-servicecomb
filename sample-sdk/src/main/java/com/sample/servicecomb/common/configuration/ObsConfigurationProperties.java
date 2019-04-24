package com.sample.servicecomb.common.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName ObsConfigurationProperties
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/3/7 15:12
 */
@ConfigurationProperties(value = "huawei.obs")
public class ObsConfigurationProperties {
    private String endPoint = "obs.cn-north-1.myhuaweicloud.com";
    private Integer socketTimeout = 30000;
    private Integer connectionTimeout = 10000;
    private Boolean httpsOnly = true;
    private String ak = "3TWL7VJQ4IM45X3DAMLF";
    private String sk = "i1aCblcgP4ZyoUwBHZnWd4rJCb5skN2Ya0LzSix5";

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Boolean getHttpsOnly() {
        return httpsOnly;
    }

    public void setHttpsOnly(Boolean httpsOnly) {
        this.httpsOnly = httpsOnly;
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }
}
