package com.sample.servicecomb.provider.controller;

import com.sample.servicecomb.common.api.IUserController;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.web.bind.annotation.*;
import com.sample.servicecomb.common.bean.User;
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
        user.setId(id);
        user.setUserName("胡亚曦");
        user.setAge(27);
        return user;
    }
}
