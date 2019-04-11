package com.sample.servicecomb.consumer.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

/**
 * @ClassName TestValue
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/4/11 9:50
 */
@Service
@RefreshScope
public class TestValue {
    @Value("${author.name}")
    private String value;

    public String getValue() {
        return value;
    }
}
