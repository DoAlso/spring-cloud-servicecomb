package com.sample.servicecomb.config.client;

import org.apache.commons.configuration.Configuration;
/**
 * @ClassName CloudConfig
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/4/22 10:52
 */
public class CloudConfig {
    public static final CloudConfig INSTANCE = new CloudConfig();
    private static Configuration finalConfig;
    private static final String SERVER_URL_KEY = "spring.cloud.config.uri";
    private static final String CLOUD_SERVICE_NAME  = "spring.application.name";
    private static final String SERVICE_ENV = "spring.cloud.config.profile";
    private static final String SERVICE_LABEL = "spring.cloud.config.label";
    private static final String TOKEN = "apollo.config.token";
    private static final String REFRESH_INTERVAL = "apollo.config.refreshInterval";
    private static final String FIRST_REFRESH_INTERVAL = "apollo.config.firstRefreshInterval";
    private static final int DEFAULT_REFRESH_INTERVAL = 3;
    private static final int DEFAULT_FIRST_REFRESH_INTERVAL = 0;
    private CloudConfig() {
    }

    public static void setConcurrentCompositeConfiguration(Configuration config) {
        finalConfig = config;
    }
    public Configuration getConcurrentCompositeConfiguration() {
        return finalConfig;
    }

    public String getServiceName() {
        return finalConfig.getString(CLOUD_SERVICE_NAME);
    }

    public String getServerUri() {
        return finalConfig.getString(SERVER_URL_KEY);
    }

    public String getToken() {
        return finalConfig.getString(TOKEN);
    }

    public String getEnv() {
        return finalConfig.getString(SERVICE_ENV);
    }

    public String getLabel() {
        return finalConfig.getString(SERVICE_LABEL);
    }

    public int getRefreshInterval() {
        return finalConfig.getInt(REFRESH_INTERVAL, DEFAULT_REFRESH_INTERVAL);
    }

    public int getFirstRefreshInterval() {
        return finalConfig.getInt(FIRST_REFRESH_INTERVAL, DEFAULT_FIRST_REFRESH_INTERVAL);
    }
}
