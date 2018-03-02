//package com.servicecomb.cache.core;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.TimeUnit;
//
//@Component
//public class RedisClient {
//
//    private StringRedisTemplate template;
//
//    @Autowired
//    public RedisClient(StringRedisTemplate template){
//        this.template = template;
//    }
//
//    public void set(String key,String value) {
//        template.boundValueOps(key).set(value);
//    }
//
//    public void set(String key,String value,int expire) {
//        template.boundValueOps(key).set(value,expire);
//    }
//
//    public void setH(String key,String value,int expire) {
//        template.boundValueOps(key).set(value,expire, TimeUnit.HOURS);
//    }
//
//    public String get(String key){
//        return template.boundValueOps(key).get();
//    }
//
//    public void expire(String key,int expire) {
//        template.boundValueOps(key).expire(expire,TimeUnit.HOURS);
//    }
//
//}
