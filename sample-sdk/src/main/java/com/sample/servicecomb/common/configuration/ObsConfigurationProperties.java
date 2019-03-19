package com.sample.servicecomb.common.configuration;

import io.vertx.core.http.HttpServer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

/**
 * @ClassName ObsConfigurationProperties
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/3/7 15:12
 */
@ConfigurationProperties(value = "huawei.obs")
public class ObsConfigurationProperties {
    private static Logger LOGGER = LoggerFactory.getLogger(ObsConfigurationProperties.class);
    private static final String CLASS_PATH = "classpath:";
    private String endPoint;
    private Integer socketTimeout;
    private Integer connectionTimeout;
    private Boolean httpsOnly;
    private String ak;
    private String sk;
    private String downPath;

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

    public String getDownPath() {
        if(StringUtils.isNotBlank(downPath)){
            return downPath;
        }
        try {
            downPath = ResourceUtils.getURL(CLASS_PATH).getPath();
        }catch (FileNotFoundException e){
            LOGGER.error("errorMsg:{},cause:{}",e.getMessage(),e.getCause());
        }
        return downPath;
    }

    public void setDownPath(String downPath) {
        this.downPath = downPath;
    }
}
