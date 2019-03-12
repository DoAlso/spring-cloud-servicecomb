package com.sample.servicecomb.consumer.rpc;

import com.sample.servicecomb.api.provider.IUserController;
import com.sample.servicecomb.api.provider.bo.UserBO;
import org.apache.servicecomb.provider.pojo.Invoker;
import org.apache.servicecomb.provider.pojo.RpcReference;
import org.springframework.stereotype.Component;

/**
 * @ClassName UserRpcClient
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/2/16 11:34
 */
@Component
public class UserRpcClient {

    @RpcReference(microserviceName = "provider",schemaId = "userController")
    private IUserController userController;

    public UserBO getUserById(Long id){
        return userController.getUserById(id);
    }

    public UserBO getUserByIdV1(Long id) {
        IUserController userController = Invoker.createProxy("provider","userController",IUserController.class);
        return userController.getUserById(id);
    }
}
