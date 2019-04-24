package com.sample.servicecomb.common.configuration;

import com.sample.servicecomb.common.obs.ObsClientUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ObsAutoConfiguration
 * @Author huyaxi
 * @DATE 2019/3/7 15:13
 */
@Configuration
@EnableConfigurationProperties(ObsConfigurationProperties.class)
public class ObsAutoConfiguration {

    @Bean
    @ConditionalOnBean(ObsConfigurationProperties.class)
    public ObsClientUtil getObsClientUtil(ObsConfigurationProperties properties){
        ObsClientUtil obsClientUtil = new ObsClientUtil();
        obsClientUtil.setObsProperties(properties);
        obsClientUtil.getInstance();
        return obsClientUtil;
    }
}
