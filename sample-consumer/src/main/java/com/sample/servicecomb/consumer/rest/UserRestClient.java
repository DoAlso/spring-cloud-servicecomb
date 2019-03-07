package com.sample.servicecomb.consumer.rest;

import com.sample.servicecomb.common.bean.provider.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName UserRestClient
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/1/22 18:09
 */
@Component
public class UserRestClient {
    @Autowired
    private RestTemplate restTemplate;

    public User getUserById(Long id){
        return restTemplate.getForEntity("cse://provider/user/"+id,User.class,id).getBody();
    }
}
