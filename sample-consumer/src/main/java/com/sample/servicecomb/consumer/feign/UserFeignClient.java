package com.sample.servicecomb.consumer.feign;

import com.sample.servicecomb.common.bean.User;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @ClassName UserFeignClient
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/1/21 17:37
 */
@FeignClient(name = "provider",fallback = UserFeignClient.UserFeignClientFallback.class)
public interface UserFeignClient {

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id);

    @Component
    class UserFeignClientFallback implements UserFeignClient{
        @Override
        public User getUserById(Long id) {
            User user = new User();
            user.setId(id);
            user.setUserName("错误数据");
            user.setAge(-1);
            return user;
        }
    }


    @Component
    class UserFeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {

        @Override
        public UserFeignClient create(Throwable throwable) {
            return null;
        }
    }
}
