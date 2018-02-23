package com.servicecomb.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ImportResource;

@SpringCloudApplication
@EnableAutoConfiguration
@ImportResource(locations = "classpath*:META-INF/spring/*.bean.xml")
@MapperScan(basePackages = {"com.servicecomb.common.dao"})
public class BaseApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseApiApplication.class, args);
	}
}
