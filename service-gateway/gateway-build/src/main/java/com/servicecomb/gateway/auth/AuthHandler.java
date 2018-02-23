package com.servicecomb.gateway.auth;

import com.servicecomb.gateway.filter.AuthFilter;
import com.servicecomb.gateway.gatewayconsumer.CacheClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthHandler implements InitializingBean{
    @Autowired
    private CacheClient storageClient;
    @Override
    public void afterPropertiesSet() throws Exception {
        AuthFilter.setAuthHandler(this);
    }

    /**
     * 权限验证
     * @param path
     * @param path
     * @return
     */
    public boolean checkAuth(String path){
        return true;
    }
}
