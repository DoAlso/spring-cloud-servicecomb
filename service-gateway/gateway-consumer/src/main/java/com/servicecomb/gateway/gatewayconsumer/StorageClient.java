package com.servicecomb.gateway.gatewayconsumer;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.servicecomb.common.constant.Constant;
import com.servicecomb.common.model.BackEntity;
import com.servicecomb.common.model.vo.CurrentUser;
import com.servicecomb.common.utils.BackEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StorageClient {
    @Autowired(required = false)
    private RestTemplate restTemplate;

    /**
     * 获取redis保存的当前登录用户
     * @param key
     * @return
     */
    @HystrixCommand(fallbackMethod = "getCurrentUserFallBack")
    public BackEntity getCurrentUser(String key){
        BackEntity backEntity = restTemplate.postForObject("http://storage/getCurrentUser",key,BackEntity.class);
        return backEntity;
    }

    /**
     * 请求失败后的降级处理
     * @param key
     * @return
     */
    public BackEntity getCurrentUserFallBack(String key){
        CurrentUser user = new CurrentUser();
        user.setToken("1_dfg784kjs455kfg9454kjfg");
        user.setAccount("admin");
        return BackEntityUtil.getReponseResult(user, Constant.ResponseMSG.REQUEST_ERROR,Constant.ResponseCode.SYSTEM_BUSY);
    }

    /**
     * 获取当前登录用户
     * 保存在redis中的权限
     * @param key userId
     * @return
     */
    @HystrixCommand(fallbackMethod = "getAuthInfoFallBack")
    public BackEntity getAuthInfo(String key){
        BackEntity backEntity = restTemplate.postForObject("http://storage/getAuthInfo",key,BackEntity.class);
        return backEntity;
    }

    /**
     * 请求失败后的降级处理
     * @param key
     * @return
     */
    public BackEntity getAuthInfoFallBack(String key){
        return BackEntityUtil.getReponseResult(null, Constant.ResponseMSG.REQUEST_ERROR,Constant.ResponseCode.SYSTEM_BUSY);
    }
}
