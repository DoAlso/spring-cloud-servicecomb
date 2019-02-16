package com.sample.servicecomb.consumer.controller;

import com.sample.servicecomb.consumer.rest.DemoRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DemoController
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/2/16 13:40
 */
@RestController
public class DemoController {
    @Autowired
    private DemoRestClient demoRestClient;

    @GetMapping("/sayHi")
    public String sayHi(String name){
        return demoRestClient.sayHi(name);
    }
}
