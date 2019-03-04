package com.sample.servicecomb.scca.rest;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName SccaRestAutoConfiguration
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/3/4 17:48
 */
@Configuration
@EnableConfigurationProperties(SccaRestProperties.class)
@PropertySource("scca-rest.properties")
public class SccaRestAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes();
    }

    @Bean
    @ConditionalOnMissingBean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
