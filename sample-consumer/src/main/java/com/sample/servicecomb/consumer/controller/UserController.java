package com.sample.servicecomb.consumer.controller;

import com.sample.servicecomb.api.provider.bo.UserBO;
import com.sample.servicecomb.consumer.model.vo.UserVO;
import com.sample.servicecomb.consumer.rest.UserRestClient;
import com.sample.servicecomb.consumer.rpc.UserRpcClient;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.BeanUtils;
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
    private UserRestClient userRestClient;
    @Resource
    private UserRpcClient userRpcClient;

    @GetMapping("v1/user/{id}")
    public UserVO getUserByIdV1(@PathVariable Long id) {
        UserBO userBO = userRpcClient.getUserByIdV1(id);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userBO,userVO);
        return userVO;
    }

    @GetMapping("v2/user/{id}")
    public UserVO getUserByIdV2(@PathVariable Long id) {
        UserBO userBO = userRpcClient.getUserById(id);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userBO,userVO);
        return userVO;
    }

    @GetMapping("/v3/user/{id}")
    public UserVO getUserByIdV3(@PathVariable Long id){
        UserBO userBO = userRestClient.getUserById(id);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userBO,userVO);
        return userVO;
    }
}
