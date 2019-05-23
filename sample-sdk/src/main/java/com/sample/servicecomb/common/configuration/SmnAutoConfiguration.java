package com.sample.servicecomb.common.configuration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ObsAutoConfiguration
 * @Author huyaxi
 * @DATE 2019/3/7 15:13
 */
@Configuration
@AutoConfigureAfter(SdkCoreConfiguration.class)
@EnableConfigurationProperties(SmnConfigurationProperties.class)
public class SmnAutoConfiguration {

}
