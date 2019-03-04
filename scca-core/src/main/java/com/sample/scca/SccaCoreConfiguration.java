package com.sample.scca;

import com.sample.scca.service.BaseOptService;
import com.sample.scca.service.PropertiesService;
import com.sample.scca.service.UrlMakerService;
import com.sample.scca.service.impl.BaseOptServiceImpl;
import com.sample.scca.service.impl.BaseUrlMaker;
import com.sample.scca.service.impl.PropertiesServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @ClassName SccaCoreConfiguration
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/3/4 15:25
 */
@Configuration
@EnableConfigurationProperties(SccaCoreProperties.class)
@EntityScan("com.sample.scca.domain")
@EnableJpaRepositories(basePackages = "com.sample.scca.domain")
public class SccaCoreConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public BaseOptService optService() {
        return new BaseOptServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public PropertiesService propertiesService() {
        return new PropertiesServiceImpl();
    }



    /**
     * 不使用基于服务的配置中心配置
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnMissingClass({
            "com.didispace.scca.service.discovery.consul.UrlMaker4Consul",
            "com.didispace.scca.service.discovery.eureka.UrlMaker4Eureka"
    })
    public UrlMakerService urlMakerService() {
        return new BaseUrlMaker();
    }
}
