package com.servicecomb.gateway;

import com.servicecomb.gateway.filter.AuthFilter;
import com.servicecomb.gateway.filter.PassFilter;
import com.servicecomb.gateway.filter.TokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.client.RestTemplate;

@EnableZuulProxy
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ImportResource(locations = "classpath*:META-INF/spring/*.bean.xml")
@SpringCloudApplication
public class GatewayBuildApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayBuildApplication.class, args);
	}

	/**
	 * 实例化过滤器
	 * @return
	 */
	@Bean
	public PassFilter passFilter() {
		return new PassFilter();
	}

	@Bean
	public TokenFilter tokenFilter(){
		return new TokenFilter();
	}

	@Bean
	public AuthFilter authFilter(){
		return new AuthFilter();
	}

	@Bean
	@LoadBalanced
	RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
