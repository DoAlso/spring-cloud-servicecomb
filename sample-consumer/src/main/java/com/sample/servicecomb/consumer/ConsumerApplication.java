package com.sample.servicecomb.consumer;

import org.apache.servicecomb.provider.springmvc.reference.RestTemplateBuilder;
import org.apache.servicecomb.springboot2.starter.EnableServiceComb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName ConsumerApplication
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/1/21 17:07
 */
@EnableDiscoveryClient
@EnableServiceComb
@SpringBootApplication
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return RestTemplateBuilder.create();
    }
}
