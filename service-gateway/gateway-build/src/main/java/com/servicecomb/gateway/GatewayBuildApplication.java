package com.servicecomb.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ImportResource;

@EnableZuulProxy
@SpringCloudApplication
@EnableAutoConfiguration
@ImportResource(locations = "classpath*:META-INF/spring/*.bean.xml")
public class GatewayBuildApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayBuildApplication.class, args);
	}
}
