package com.sample.servicecomb.common.configuration;

import com.sample.servicecomb.common.dis.DisClientUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
@EnableConfigurationProperties(DisConfigurationProperties.class)
public class DisAutoConfiguration {


    @Bean
    @ConditionalOnBean(DisConfigurationProperties.class)
    public DisClientUtil getDisClientUtil(DisConfigurationProperties properties){
        DisClientUtil disClientUtil = new DisClientUtil();
        disClientUtil.setDisProperties(properties);
        disClientUtil.getInstance();
        return disClientUtil;
    }

}
