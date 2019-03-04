package com.sample.servicecomb.rest;

import org.apache.servicecomb.springboot2.starter.EnableServiceComb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName SccaConfigCenterApplication
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/3/4 11:32
 */
@EnableServiceComb
@SpringBootApplication
public class SccaConfigCenterApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SccaConfigCenterApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }
}
