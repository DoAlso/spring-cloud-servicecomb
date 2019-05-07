package com.sample.servicecomb.common.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName SmnConfigurationProperties
 * @Author huyaxi
 * @DATE 2019/4/29 9:44
 */
@ConfigurationProperties(value = "huawei.smn")
public class SmnConfigurationProperties {
    private String ak = "3TWL7VJQ4IM45X3DAMLF";
    private String sk = "i1aCblcgP4ZyoUwBHZnWd4rJCb5skN2Ya0LzSix5";
    private String region = "cn-north-1";
    private String signId = "d33ad62731e24162b2c9436ba93f7296";

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

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
