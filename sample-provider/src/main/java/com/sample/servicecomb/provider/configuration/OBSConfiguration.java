package com.sample.servicecomb.provider.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName OBSConfiguration
 * @Description TODO
 * @Author 胡亚曦
 * @DATE 2019/2/13 9:55
 */
@ConfigurationProperties(value = "huawei.obs")
public class OBSConfiguration {
    private String endPoint;
    private Integer socketTimeout;
    private Integer connectionTimeout;
    private Boolean httpsOnly;
    private String ak;
    private String sk;

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
