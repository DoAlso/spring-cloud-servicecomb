package com.sample.servicecomb.consumer.controller;

import com.sample.servicecomb.consumer.feign.UserFeignClient;
import com.sample.servicecomb.common.bean.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author doalso
 */
@RestController
public class UserController {

    @Resource
    private UserFeignClient userFeignClient;

    @GetMapping("/user/{id}")
    public User dc(@PathVariable Long id) {
        return userFeignClient.getUserById(id);
    }
}
