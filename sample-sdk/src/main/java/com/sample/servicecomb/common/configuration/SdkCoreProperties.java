package com.sample.servicecomb.common.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName SdkCoreProperties
 * @Author huyaxi
 * @DATE 2019/5/23 13:33
 */

@ConfigurationProperties(prefix = "sdk")
public class SdkCoreProperties {
    private Boolean obsEnable = true;
    private Boolean frsEnable = true;
    private Boolean smnEnable = true;
    private Boolean disEnable = true;

    public Boolean getObsEnable() {
        return obsEnable;
    }

    public void setObsEnable(Boolean obsEnable) {
        this.obsEnable = obsEnable;
    }

    public Boolean getFrsEnable() {
        return frsEnable;
    }

    public void setFrsEnable(Boolean frsEnable) {
        this.frsEnable = frsEnable;
    }

    public Boolean getSmnEnable() {
        return smnEnable;
    }

    public void setSmnEnable(Boolean smnEnable) {
        this.smnEnable = smnEnable;
    }

    public Boolean getDisEnable() {
        return disEnable;
    }

    public void setDisEnable(Boolean disEnable) {
        this.disEnable = disEnable;
    }
}
