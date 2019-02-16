package com.sample.servicecomb.consumer.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName DemoRestClient
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/2/16 13:37
 */
@Component
public class DemoRestClient {
    @Autowired
    private RestTemplate restTemplate;

    public String sayHi(String name){
        return restTemplate.getForEntity("http://demo/sayHi",String.class,name).getBody();
    }
}
