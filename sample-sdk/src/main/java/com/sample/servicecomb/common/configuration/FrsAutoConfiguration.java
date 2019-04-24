package com.sample.servicecomb.common.configuration;

import com.sample.servicecomb.common.frs.FrsClientUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
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

    @Bean
    @ConditionalOnBean(FrsConfigurationProperties.class)
    public FrsClientUtil getFrsClientUtil(FrsConfigurationProperties properties){
        FrsClientUtil frsClientUtil = new FrsClientUtil();
        frsClientUtil.setFrsProperties(properties);
        frsClientUtil.getInstance();
        return frsClientUtil;
    }
}
