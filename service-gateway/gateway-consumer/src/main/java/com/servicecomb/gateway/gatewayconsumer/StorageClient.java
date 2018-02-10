package com.servicecomb.gateway.gatewayconsumer;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.servicecomb.common.constant.Constant;
import com.servicecomb.common.model.BackEntity;
import com.servicecomb.common.utils.BackEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StorageClient {
    @Autowired(required = false)
    private RestTemplate restTemplate;

    /**
     * @param key
     * @return
     */
    @HystrixCommand(fallbackMethod = "getAuthInfoFallBack")
    public BackEntity getAuthInfo(String key){
        BackEntity backEntity = restTemplate.postForObject("http://storage/getAuthInfo",key,BackEntity.class);
        return backEntity;
    }

    public BackEntity getAuthInfoFallBack(String key){
        return BackEntityUtil.getRequestResult(null, Constant.ResponseMSG.REQUEST_ERROR,Constant.ResponseCode.SYSTEM_BUSY);
    }

    /**
     *
     * @return
     */
    @HystrixCommand(fallbackMethod = "getMenuInfoFallBack")
    public BackEntity getMenuInfo(){
        BackEntity backEntity = restTemplate.postForObject("http://storage/getMenuInfo",null,BackEntity.class);
        return backEntity;
    }

    public BackEntity getMenuInfoFallBack(){
        return BackEntityUtil.getRequestResult(null, Constant.ResponseMSG.REQUEST_ERROR,Constant.ResponseCode.SYSTEM_BUSY);
    }
}
