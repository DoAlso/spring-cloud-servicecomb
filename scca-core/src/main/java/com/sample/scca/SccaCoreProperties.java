package com.sample.scca;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by 程序猿DD/翟永超 on 2018/5/2.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */

@ConfigurationProperties("scca")
public class SccaCoreProperties {

    /**
     * 默认的label名称
     */
    private String defaultLabel = "master";

    private String repositoryType = "git";

    public String getDefaultLabel() {
        return defaultLabel;
    }

    public void setDefaultLabel(String defaultLabel) {
        this.defaultLabel = defaultLabel;
    }

    public String getRepositoryType() {
        return repositoryType;
    }

    public void setRepositoryType(String repositoryType) {
        this.repositoryType = repositoryType;
    }
}
