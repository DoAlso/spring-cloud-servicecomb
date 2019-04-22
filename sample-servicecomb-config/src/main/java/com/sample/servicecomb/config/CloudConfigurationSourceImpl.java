package com.sample.servicecomb.config;

import com.google.common.collect.ImmutableMap;
import com.netflix.config.WatchedUpdateListener;
import com.netflix.config.WatchedUpdateResult;
import com.sample.servicecomb.config.client.CloudConfig;
import com.sample.servicecomb.config.client.CloudConfigClient;
import com.sample.servicecomb.config.client.ConfigurationAction;
import org.apache.commons.configuration.Configuration;
import org.apache.servicecomb.config.ConfigMapping;
import org.apache.servicecomb.config.spi.ConfigCenterConfigurationSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.netflix.config.WatchedUpdateResult.createIncremental;
import static com.sample.servicecomb.config.client.ConfigurationAction.CREATE;
import static com.sample.servicecomb.config.client.ConfigurationAction.SET;
import static com.sample.servicecomb.config.client.ConfigurationAction.DELETE;

/**
 * @ClassName CloudConfigurationSourceImpl
 * @Author Administrator
 * @DATE 2019/4/22 10:38
 */
public class CloudConfigurationSourceImpl implements ConfigCenterConfigurationSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(CloudConfigurationSourceImpl.class);
    private final Map<String, Object> valueCache = new ConcurrentHashMap<>();
    private List<WatchedUpdateListener> listeners = new CopyOnWriteArrayList<>();
    private final UpdateHandler updateHandler = new UpdateHandler();

    public CloudConfigurationSourceImpl() {
    }

    @Override
    public boolean isValidSource(Configuration configuration) {
        return true;
    }

    @Override
    public void init(Configuration configuration) {
        CloudConfig.setConcurrentCompositeConfiguration(configuration);
        init();
    }

    private void init(){
        CloudConfigClient cloudConfigClient = new CloudConfigClient(updateHandler);
        cloudConfigClient.refreshCloudConfig();
    }

    @Override
    public void addUpdateListener(@NotNull WatchedUpdateListener watchedUpdateListener) {
        listeners.add(watchedUpdateListener);
    }

    @Override
    public void removeUpdateListener(@NotNull WatchedUpdateListener watchedUpdateListener) {
        listeners.remove(watchedUpdateListener);
    }

    private void updateConfiguration(WatchedUpdateResult result) {
        for (WatchedUpdateListener l : listeners) {
            try {
                l.updateConfiguration(result);
            } catch (Throwable ex) {
                LOGGER.error("Error in invoking WatchedUpdateListener", ex);
            }
        }
    }

    @Override
    public Map<String, Object> getCurrentData() throws Exception {
        return valueCache;
    }

    public List<WatchedUpdateListener> getCurrentListeners() {
        return listeners;
    }


    public class UpdateHandler {
        public void handle(ConfigurationAction action, Map<String, Object> config) {
            if (config == null || config.isEmpty()) {
                return;
            }
            Map<String, Object> configuration = ConfigMapping.getConvertedMap(config);
            if (CREATE.equals(action)) {
                valueCache.putAll(configuration);

                updateConfiguration(createIncremental(ImmutableMap.copyOf(configuration),
                        null,
                        null));
            } else if (SET.equals(action)) {
                valueCache.putAll(configuration);

                updateConfiguration(createIncremental(null,
                        ImmutableMap.copyOf(configuration),
                        null));
            } else if (DELETE.equals(action)) {
                for (String itemKey : configuration.keySet()) {
                    valueCache.remove(itemKey);
                }
                updateConfiguration(createIncremental(null,
                        null,
                        ImmutableMap.copyOf(configuration)));
            } else {
                LOGGER.error("action: {} is invalid.", action.name());
                return;
            }
            LOGGER.warn("Config value cache changed: action:{}; item:{}", action.name(), configuration.keySet());
        }
    }
}
