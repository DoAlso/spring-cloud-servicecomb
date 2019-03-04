package com.sample.scca;

import com.sample.scca.service.PersistenceService;
import com.sample.scca.service.impl.GitPersistenceService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 程序猿DD/翟永超 on 2018/5/2.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Configuration
@EnableConfigurationProperties({SccaCoreProperties.class,SccaGitProperties.class})
public class SccaGitConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "scca", name = "repositoryType", havingValue = "git")
    public PersistenceService persistenceService() {
        return new GitPersistenceService();
    }

}
