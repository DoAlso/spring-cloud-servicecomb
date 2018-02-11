package com.servicecomb.gateway.auth;

import com.servicecomb.gateway.filter.AuthFilter;
import com.servicecomb.gateway.gatewayconsumer.StorageClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthHandler implements InitializingBean{
    @Autowired
    private StorageClient storageClient;
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
