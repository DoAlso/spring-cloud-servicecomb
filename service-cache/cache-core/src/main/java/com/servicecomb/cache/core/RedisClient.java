package com.servicecomb.cache.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisClient {

    private RedisTemplate<String,String> redis;

    @Autowired
    public void setRedis(RedisTemplate<String,String> redis) {
        this.redis = redis;
        redis.setKeySerializer(new JdkSerializationRedisSerializer());
    }

    public void set(String key,String value) {
        redis.boundValueOps(key).set(value);
    }

    public void set(String key,String value,int expire) {
        redis.boundValueOps(key).set(value,expire);
    }

    public void setH(String key,String value,int expire) {
        redis.boundValueOps(key).set(value,expire, TimeUnit.HOURS);
    }

    public String get(String key){
        return redis.boundValueOps(key).get();
    }

    public void expire(String key,int expire) {
        redis.boundValueOps(key).expire(expire,TimeUnit.HOURS);
    }

}
