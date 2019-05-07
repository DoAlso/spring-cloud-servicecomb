package com.sample.servicecomb.common.configuration;

import com.sample.servicecomb.common.smn.SmnClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ObsAutoConfiguration
 * @Author huyaxi
 * @DATE 2019/3/7 15:13
 */
@Configuration
@EnableConfigurationProperties(SmnConfigurationProperties.class)
public class SmnAutoConfiguration {
    @Autowired
    private SmnConfigurationProperties properties;

    @Bean
    public SmnClientUtil getSmnClientUtil(){
        SmnClientUtil smnClientUtil = new SmnClientUtil();
        smnClientUtil.setProperties(properties);
        smnClientUtil.getInstance();
        return smnClientUtil;
    }
}
