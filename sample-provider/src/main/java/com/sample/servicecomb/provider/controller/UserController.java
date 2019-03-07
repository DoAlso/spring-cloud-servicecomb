package com.sample.servicecomb.provider.controller;

import com.sample.servicecomb.common.api.provider.IUserController;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.web.bind.annotation.*;
import com.sample.servicecomb.common.bean.provider.User;
/**
 * @ClassName UserController
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/1/21 17:14
 */

@RestController
@RestSchema(schemaId = "userController")
public class UserController implements IUserController {

    @GetMapping("/user/{id}")
    @Override
    public User getUserById(@PathVariable Long id){
        User user = new User();
        user.setUserId(id);
        user.setUsername("胡亚曦");
        user.setAccount("admin");
        user.setPassword("123456");
        return user;
    }

    @GetMapping("/login")
    public User login(String account,String password){
        if("admin".equals(account) && "123456".equals(password)){
            User user = new User();
            user.setUserId(1L);
            user.setUsername("胡亚曦");
            user.setAccount("admin");
            user.setPassword("123456");
            return user;
        }
        return null;
    }
}
