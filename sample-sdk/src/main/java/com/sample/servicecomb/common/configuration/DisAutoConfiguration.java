package com.sample.servicecomb.common.configuration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ObsAutoConfiguration
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/3/7 15:13
 */
@Configuration
@AutoConfigureAfter(SdkCoreConfiguration.class)
@EnableConfigurationProperties(DisConfigurationProperties.class)
public class DisAutoConfiguration {

}
