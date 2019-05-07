package com.sample.servicecomb.config.client;

import com.sample.servicecomb.config.CloudConfigurationSourceImpl.UpdateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.sample.servicecomb.config.client.ConfigurationAction.CREATE;
import static com.sample.servicecomb.config.client.ConfigurationAction.SET;
import static com.sample.servicecomb.config.client.ConfigurationAction.DELETE;

/**
 * @ClassName CloudConfigClient
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/4/19 15:56
 */
public class CloudConfigClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(CloudConfigClient.class);
    private static final CloudConfig CLOUD_CONFIG = CloudConfig.INSTANCE;
    private final UpdateHandler updateHandler;
    private static RestTemplate rest = new RestTemplate();
    private static final Map<String, Object> originalConfigMap = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService EXECUTOR = Executors.newScheduledThreadPool(1);
    private final int refreshInterval = CLOUD_CONFIG.getRefreshInterval();
    private final int firstRefreshInterval = CLOUD_CONFIG.getFirstRefreshInterval();
    private final String serviceUri = CLOUD_CONFIG.getServerUri();
    private final String serviceName = CLOUD_CONFIG.getServiceName();
    private final String token = CLOUD_CONFIG.getToken();
    private final String env = CLOUD_CONFIG.getEnv();
    private final String label = CLOUD_CONFIG.getLabel();

    public CloudConfigClient(UpdateHandler updateHandler){
        this.updateHandler = updateHandler;
    }


    public void refreshCloudConfig(){
        ConfigRefresh configRefresh = new ConfigRefresh(serviceUri,serviceName,env,label);
        EXECUTOR.scheduleWithFixedDelay(configRefresh, 0, 10, TimeUnit.SECONDS);
    }

    class ConfigRefresh implements Runnable {
        private final String serviceUri;
        private final String serviceName;
        private final String env;
        private final String label;
        private final String url;
        ConfigRefresh(String serviceUri,String serviceName,String env,String label){
            this.serviceUri = serviceUri;
            this.serviceName = serviceName;
            this.env = env;
            this.label = label;
            this.url = new StringBuffer(serviceUri)
                    .append("/").append(serviceName)
                    .append("/").append(env)
                    .append("/").append(label).toString();
        }

        @Override
        public void run() {
            refreshConfig();
        }

        void refreshConfig(){
            Environment environment = rest.getForObject(url, Environment.class);
            Map map = new ConcurrentHashMap<>();
            environment.getPropertySources().forEach((item)->map.putAll(item.getSource()));
            refreshConfigItems(map);
        }
    }

    private void refreshConfigItems(Map<String, Object> map) {
        compareChangedConfig(originalConfigMap, map);
        originalConfigMap.clear();
        originalConfigMap.putAll(map);
    }

    void compareChangedConfig(Map<String, Object> before, Map<String, Object> after) {
        Map<String, Object> itemsCreated = new HashMap<>();
        Map<String, Object> itemsDeleted = new HashMap<>();
        Map<String, Object> itemsModified = new HashMap<>();
        if (before == null || before.isEmpty()) {
            updateHandler.handle(CREATE, after);
            return;
        }
        if (after == null || after.isEmpty()) {
            updateHandler.handle(DELETE, before);
            return;
        }
        for (String itemKey : after.keySet()) {
            if (!before.containsKey(itemKey)) {
                itemsCreated.put(itemKey, after.get(itemKey));
            } else if (!after.get(itemKey).equals(before.get(itemKey))) {
                itemsModified.put(itemKey, after.get(itemKey));
            }
        }
        for (String itemKey : before.keySet()) {
            if (!after.containsKey(itemKey)) {
                itemsDeleted.put(itemKey, "");
            }
        }
        updateHandler.handle(CREATE, itemsCreated);
        updateHandler.handle(SET, itemsModified);
        updateHandler.handle(DELETE, itemsDeleted);
    }

}
