package com.sample.servicecomb.consumer;

import org.apache.servicecomb.springboot2.starter.EnableServiceComb;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @ClassName ConsumerApplication
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/1/21 17:07
 */
@EnableServiceComb
@SpringCloudApplication
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }
}
