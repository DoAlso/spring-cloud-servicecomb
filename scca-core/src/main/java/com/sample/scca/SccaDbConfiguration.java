package com.sample.scca;

import com.sample.scca.service.PersistenceService;
import com.sample.scca.service.impl.DbPersistenceService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by 程序猿DD/翟永超 on 2018/5/2.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Configuration
@PropertySource("scca-persistence-db.properties")
@EnableConfigurationProperties(SccaCoreProperties.class)
public class SccaDbConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "scca", name = "repositoryType", havingValue = "db")
    public PersistenceService persistenceService() {
        return new DbPersistenceService();
    }

}
