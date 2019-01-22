package com.sample.servicecomb.provider.controller;

import org.springframework.web.bind.annotation.*;
import com.sample.servicecomb.common.bean.User;
/**
 * @ClassName UserController
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/1/21 17:14
 */
@RestController
public class UserController {

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id){
        User user = new User();
        user.setId(id);
        user.setUserName("胡亚曦");
        user.setAge(27);
        return user;
    }
}
