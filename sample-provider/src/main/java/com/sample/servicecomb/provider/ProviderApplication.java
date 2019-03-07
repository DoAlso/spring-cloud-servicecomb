package com.sample.servicecomb.provider;

import org.apache.servicecomb.springboot2.starter.EnableServiceComb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName ProviderApplication
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/1/21 17:03
 */
@EnableServiceComb
@SpringBootApplication
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ProviderApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }
}
