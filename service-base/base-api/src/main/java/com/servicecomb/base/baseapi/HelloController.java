package com.servicecomb.base.baseapi;

import com.servicecomb.base.baseprovider.HelloFacade;
import com.servicecomb.common.model.BackEntity;
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
}
