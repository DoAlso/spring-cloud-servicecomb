package com.sample.servicecomb.gateway;

import org.apache.servicecomb.springboot2.starter.EnableServiceComb;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @ClassName GatewayApplication
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/1/17 14:23
 */
@EnableServiceComb
@SpringCloudApplication
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);
    }
}
