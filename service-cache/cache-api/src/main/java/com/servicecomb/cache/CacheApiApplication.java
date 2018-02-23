package com.servicecomb.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ImportResource;

@SpringCloudApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ImportResource(locations = "classpath*:META-INF/spring/*.bean.xml")
public class CacheApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CacheApiApplication.class, args);
	}
}
