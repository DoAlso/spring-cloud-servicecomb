package com.sample.servicecomb.provider.controller;

import com.sample.servicecomb.api.provider.IUserController;
import com.sample.servicecomb.api.provider.bo.UserBO;
import com.sample.servicecomb.provider.model.bean.User;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
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
    public UserBO getUserById(@PathVariable Long id){
        UserBO userBO = new UserBO();
        User user = new User();
        user.setUserId(id);
        user.setUsername("胡亚曦");
        user.setAccount("admin");
        user.setPassword("123456");
        BeanUtils.copyProperties(user,userBO);
        return userBO;
    }

    @GetMapping("/login")
    public UserBO login(String account, String password){
        if("admin".equals(account) && "123456".equals(password)){
            UserBO userBO = new UserBO();
            User user = new User();
            user.setUserId(1L);
            user.setUsername("胡亚曦");
            user.setAccount("admin");
            user.setPassword("123456");
            BeanUtils.copyProperties(user,userBO);
            return userBO;
        }
        return null;
    }

    @PostMapping("/message")
    public String getBack(@RequestParam("param") String param){
        return param;
    }
}
