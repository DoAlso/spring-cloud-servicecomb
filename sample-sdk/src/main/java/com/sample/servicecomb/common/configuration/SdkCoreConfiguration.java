package com.sample.servicecomb.common.configuration;

import com.sample.servicecomb.common.dis.DisClientUtil;
import com.sample.servicecomb.common.frs.FrsClientUtil;
import com.sample.servicecomb.common.obs.ObsClientUtil;
import com.sample.servicecomb.common.smn.SmnClientUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName SdkCoreConfiguration
 * @Author huyaxi
 * @DATE 2019/5/23 13:35
 */
@Configuration
@EnableConfigurationProperties(SdkCoreProperties.class)
public class SdkCoreConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "sdk",name = "smnEnable",havingValue = "true")
    public SmnClientUtil getSmnClientUtil(SmnConfigurationProperties properties){
        SmnClientUtil smnClientUtil = new SmnClientUtil();
        smnClientUtil.setProperties(properties);
        smnClientUtil.getInstance();
        return smnClientUtil;
    }


    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "sdk",name = "disEnable",havingValue = "true")
    public DisClientUtil getDisClientUtil(DisConfigurationProperties properties){
        DisClientUtil disClientUtil = new DisClientUtil();
        disClientUtil.setDisProperties(properties);
        disClientUtil.getInstance();
        return disClientUtil;
    }


    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "sdk",name = "frsEnable",havingValue = "true")
    public FrsClientUtil getFrsClientUtil(FrsConfigurationProperties properties) throws Exception{
        FrsClientUtil frsClientUtil = new FrsClientUtil();
        frsClientUtil.setFrsProperties(properties);
        frsClientUtil.getInstance();
        return frsClientUtil;
    }


    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "sdk",name = "obsEnable",havingValue = "true")
    public ObsClientUtil getObsClientUtil(ObsConfigurationProperties properties){
        ObsClientUtil obsClientUtil = new ObsClientUtil();
        obsClientUtil.setObsProperties(properties);
        obsClientUtil.getInstance();
        return obsClientUtil;
    }
}
