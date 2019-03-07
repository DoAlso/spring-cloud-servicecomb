package com.sample.servicecomb.consumer.rpc;

import com.sample.servicecomb.common.api.provider.IUserController;
import com.sample.servicecomb.common.bean.provider.User;
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

    public User getUserById(Long id){
        return userController.getUserById(id);
    }

    public User getUserByIdV1(Long id) {
        IUserController userController = Invoker.createProxy("provider","userController",IUserController.class);
        return userController.getUserById(id);
    }
}
