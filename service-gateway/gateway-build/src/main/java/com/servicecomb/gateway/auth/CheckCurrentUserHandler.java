package com.servicecomb.gateway.auth;

import com.servicecomb.common.model.vo.CurrentUser;
import com.servicecomb.gateway.filter.TokenFilter;
import com.servicecomb.gateway.gatewayconsumer.StorageClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 315238976@qq.com
 * @date 2018/2/7 19:21
 */
@Component
public class CheckCurrentUserHandler implements InitializingBean {
    private Integer tokenLength = 2;//token截取后长度

    @Autowired
    private StorageClient storageClient;

    @Override
    public void afterPropertiesSet() throws Exception {
        TokenFilter.setCheckCurrentUserHandler(this);
    }

    /**
     * 鉴权操作
     * @param xToken
     * @return
     */
    public CurrentUser checkCurrentUser(String xToken){
        if(StringUtils.isBlank(xToken)){
            return null;
        }
        String[] param = xToken.split("_");
        if(param.length != tokenLength){
            return null;
        }
        CurrentUser currentUser = (CurrentUser)storageClient.getCurrentUser(param[0]).getResData();
        if(currentUser == null || !xToken.equals(currentUser.getToken())){
            return null;
        }
        return currentUser;
    }

}
