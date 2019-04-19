package com.sample.servicecomb.config;

import com.google.common.collect.ImmutableMap;
import com.netflix.config.WatchedUpdateListener;
import com.netflix.config.WatchedUpdateResult;
import org.apache.commons.configuration.Configuration;
import org.apache.servicecomb.config.ConfigMapping;
import org.apache.servicecomb.config.spi.ConfigCenterConfigurationSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.netflix.config.WatchedUpdateResult.createIncremental;

/**
 * @ClassName CloudConfigClient
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/4/19 15:56
 */
public class CloudConfigClient implements
        ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered, ConfigCenterConfigurationSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(CloudConfigClient.class);
    private static final Map<String, Object> originalConfigMap = new ConcurrentHashMap<>();
    private final UpdateHandler updateHandler = new UpdateHandler();
    private final Map<String, Object> valueCache = new ConcurrentHashMap<>();

    private List<WatchedUpdateListener> listeners = new CopyOnWriteArrayList<>();

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        LOGGER.info("Starting Refresh Configuration .......................");
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        Map<String,Object> properties = getAllProperties(environment);
        refreshConfigItems(properties);
    }

    @Override
    public boolean isValidSource(Configuration configuration) {
        return true;
    }

    @Override
    public void init(Configuration configuration) {

    }

    @Override
    public void addUpdateListener(@NotNull WatchedUpdateListener watchedUpdateListener) {
        listeners.add(watchedUpdateListener);
    }

    @Override
    public void removeUpdateListener(@NotNull WatchedUpdateListener watchedUpdateListener) {
        listeners.remove(watchedUpdateListener);
    }

    @Override
    public Map<String, Object> getCurrentData() throws Exception {
        return valueCache;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Map<String, Object> getAllProperties(Environment environment) {
        Map<String, Object> configFromSpringBoot = new HashMap();
        if (!(environment instanceof ConfigurableEnvironment)) {
            return configFromSpringBoot;
        } else {
            ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment)environment;
            Iterator var4 = configurableEnvironment.getPropertySources().iterator();

            while(var4.hasNext()) {
                PropertySource<?> propertySource = (PropertySource)var4.next();
                this.getProperties(configurableEnvironment, propertySource, configFromSpringBoot);
            }

            return configFromSpringBoot;
        }
    }

    private void getProperties(ConfigurableEnvironment environment, PropertySource<?> propertySource, Map<String, Object> configFromSpringBoot) {
        if (propertySource instanceof CompositePropertySource) {
            CompositePropertySource compositePropertySource = (CompositePropertySource)propertySource;
            compositePropertySource.getPropertySources().forEach((ps) -> {
                this.getProperties(environment, ps, configFromSpringBoot);
            });
        } else if (!(propertySource instanceof EnumerablePropertySource)) {
            LOGGER.debug("a none EnumerablePropertySource is ignored, propertySourceName = [{}]", propertySource.getName());
        } else {
            EnumerablePropertySource<?> enumerablePropertySource = (EnumerablePropertySource)propertySource;
            String[] var5 = enumerablePropertySource.getPropertyNames();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                String propertyName = var5[var7];
                configFromSpringBoot.put(propertyName, environment.getProperty(propertyName, Object.class));
            }

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
            updateHandler.handle(ConfigurationAction.CREATE, after);
            return;
        }
        if (after == null || after.isEmpty()) {
            updateHandler.handle(ConfigurationAction.DELETE, before);
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
        updateHandler.handle(ConfigurationAction.CREATE, itemsCreated);
        updateHandler.handle(ConfigurationAction.SET, itemsModified);
        updateHandler.handle(ConfigurationAction.DELETE, itemsDeleted);
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

    public class UpdateHandler {
        public void handle(ConfigurationAction action, Map<String, Object> config) {
            if (config == null || config.isEmpty()) {
                return;
            }
            Map<String, Object> configuration = ConfigMapping.getConvertedMap(config);
            if (ConfigurationAction.CREATE.equals(action)) {
                valueCache.putAll(configuration);

                updateConfiguration(createIncremental(ImmutableMap.copyOf(configuration),
                        null,
                        null));
            } else if (ConfigurationAction.SET.equals(action)) {
                valueCache.putAll(configuration);

                updateConfiguration(createIncremental(null,
                        ImmutableMap.copyOf(configuration),
                        null));
            } else if (ConfigurationAction.DELETE.equals(action)) {
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
