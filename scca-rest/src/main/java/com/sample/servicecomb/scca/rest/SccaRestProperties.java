package com.sample.servicecomb.scca.rest;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("scca.rest")
public class SccaRestProperties {

    /**
     * scca-rest¬的contextPath
     */
    private String contextPath = "";

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
}
