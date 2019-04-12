package com.sample.servicecomb.consumer.common;

import org.apache.servicecomb.config.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName ConfigurationSpringRefreshListener
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/4/12 17:39
 */
@Component
public class ConfigurationSpringRefreshListener implements ApplicationListener<RefreshRemoteApplicationEvent> {
    private static Logger LOGGER = LoggerFactory.getLogger(ConfigurationSpringRefreshListener.class);
    private static final String PATH = "/property/persistent?";
    @Value("${spring.cloud.config.uri}")
    private String configServerUrl;
    @Value("${spring.application.name}")
    private String application;
    @Value("${spring.cloud.config.profile}")
    private String profile;
    @Value("${spring.cloud.config.label}")
    private String label;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void onApplicationEvent(RefreshRemoteApplicationEvent event) {
        LOGGER.info("==========================================");
        LOGGER.info("configServerUrl:{},application:{},profile:{},label:{}",configServerUrl,application,profile,label);
        String url = new StringBuffer(configServerUrl).append(PATH).
                append("project=").append(application)
                .append("&profile=").append(profile)
                .append("&label=").append(label).toString();
        ConfigModel configModel = restTemplate.getForObject(url,ConfigModel.class);
        ConfigUtil.addExtraConfig("extraConfig-" + application, configModel.getData());
        ConfigUtil.installDynamicConfig();
    }
}
