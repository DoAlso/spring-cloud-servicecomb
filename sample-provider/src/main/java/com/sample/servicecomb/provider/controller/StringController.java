package com.sample.servicecomb.provider.controller;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName StringController
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/2/16 14:03
 */
@RestController
@RestSchema(schemaId = "stringController")
public class StringController {

    @GetMapping("/getStrIn")
    public String getStrIn(){
        return "测试调用";
    }
}
