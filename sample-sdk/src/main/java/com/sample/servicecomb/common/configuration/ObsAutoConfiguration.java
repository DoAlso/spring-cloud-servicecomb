package com.sample.servicecomb.common.configuration;

import com.sample.servicecomb.common.obs.ObsClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ObsAutoConfiguration
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/3/7 15:13
 */
@Configuration
@EnableConfigurationProperties(ObsConfigurationProperties.class)
public class ObsAutoConfiguration {
    @Autowired
    private ObsConfigurationProperties properties;

    @Bean
    public ObsClientUtil getObsClientUtil(){
        ObsClientUtil obsClientUtil = new ObsClientUtil();
        obsClientUtil.setObsProperties(properties);
        obsClientUtil.getInstance();
        return obsClientUtil;
    }
}
