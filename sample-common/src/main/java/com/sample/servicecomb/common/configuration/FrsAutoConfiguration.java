package com.sample.servicecomb.common.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName FrsAutoConfiguration
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/3/7 14:40
 */
@Configuration
@EnableConfigurationProperties(FrsConfigurationProperties.class)
public class FrsAutoConfiguration {
}
