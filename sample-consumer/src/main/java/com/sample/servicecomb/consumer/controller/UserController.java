package com.sample.servicecomb.consumer.controller;

import com.sample.servicecomb.consumer.feign.UserFeignClient;
import com.sample.servicecomb.common.bean.User;
import com.sample.servicecomb.consumer.rest.UserRestClient;
import com.sample.servicecomb.consumer.rpc.UserRpcClient;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author doalso
 */
@RestController
@RestSchema(schemaId = "userController")
public class UserController {
    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    private UserRestClient userRestClient;
    @Resource
    private UserRpcClient userRpcClient;

    @GetMapping("v1/user/{id}")
    public User getUserByIdV1(@PathVariable Long id) {
        return userFeignClient.getUserById(id);
    }

    @GetMapping("v2/user/{id}")
    public User getUserByIdV2(@PathVariable Long id) {
        return userRpcClient.getUserById(id);
    }

    @GetMapping("/v3/user/{id}")
    public User getUserByIdV3(@PathVariable Long id){
        return userRestClient.getUserById(id);
    }
}
