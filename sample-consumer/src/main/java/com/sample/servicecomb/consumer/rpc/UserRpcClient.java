package com.sample.servicecomb.consumer.rpc;

import com.sample.servicecomb.common.api.IUserController;
import com.sample.servicecomb.common.bean.User;
import org.apache.servicecomb.provider.pojo.RpcReference;
import org.springframework.stereotype.Component;

/**
 * @ClassName UserRpcClient
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/1/22 17:51
 */
@Component
public class UserRpcClient {

    @RpcReference(microserviceName = "provider",schemaId = "userController")
    private IUserController userController;

    public User getUserById(Long id){
        return userController.getUserById(id);
    }
}
