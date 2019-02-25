package com.sample.servicecomb.show;

import org.apache.servicecomb.springboot2.starter.EnableServiceComb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName SampleShowApplication
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/2/25 17:18
 */
@EnableServiceComb
@SpringBootApplication
public class SampleShowApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SampleShowApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }
}
