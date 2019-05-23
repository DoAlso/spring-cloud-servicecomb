package com.sample.servicecomb.common.configuration;

import com.sample.servicecomb.common.dis.DisClientUtil;
import com.sample.servicecomb.common.frs.FrsClientUtil;
import com.sample.servicecomb.common.obs.ObsClientUtil;
import com.sample.servicecomb.common.smn.SmnClientUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName SdkCoreConfiguration
 * @Author huyaxi
 * @DATE 2019/5/23 13:35
 */
@Configuration
public class SdkCoreConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SmnClientUtil getSmnClientUtil(SmnConfigurationProperties properties){
        SmnClientUtil smnClientUtil = new SmnClientUtil();
        smnClientUtil.setProperties(properties);
        smnClientUtil.getInstance();
        return smnClientUtil;
    }


    @Bean
    @ConditionalOnMissingBean
    public DisClientUtil getDisClientUtil(DisConfigurationProperties properties){
        DisClientUtil disClientUtil = new DisClientUtil();
        disClientUtil.setDisProperties(properties);
        disClientUtil.getInstance();
        return disClientUtil;
    }


    @Bean
    @ConditionalOnMissingBean
    public FrsClientUtil getFrsClientUtil(FrsConfigurationProperties properties) throws Exception{
        FrsClientUtil frsClientUtil = new FrsClientUtil();
        frsClientUtil.setFrsProperties(properties);
        frsClientUtil.getInstance();
        return frsClientUtil;
    }


    @Bean
    @ConditionalOnMissingBean
    public ObsClientUtil getObsClientUtil(ObsConfigurationProperties properties){
        ObsClientUtil obsClientUtil = new ObsClientUtil();
        obsClientUtil.setObsProperties(properties);
        obsClientUtil.getInstance();
        return obsClientUtil;
    }
}
