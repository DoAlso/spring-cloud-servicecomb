package com.servicecomb.base.api;

import com.servicecomb.base.provider.HelloFacade;
import com.servicecomb.common.constant.Constant;
import com.servicecomb.common.model.BackEntity;
import com.servicecomb.common.utils.BackEntityUtil;
import com.servicecomb.common.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private HelloFacade helloFacade;

    @GetMapping("/sayHello/{name}")
    public BackEntity sayHello(@PathVariable String name){
        return helloFacade.sayHello(name);
    }

    @GetMapping("/getCurrentUser")
    public BackEntity getCurrentUser(){
        return BackEntityUtil.getReponseResult(CommonUtil.getCurrentUser(), Constant.ResponseMSG.REQUEST_OK, Constant.ResponseCode.REQUEST_OK);
    }
}
