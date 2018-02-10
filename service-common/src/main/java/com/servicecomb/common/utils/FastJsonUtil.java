package com.servicecomb.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.List;
import java.util.Map;

/**
 * Created by 胡亚曦 on 2016/12/8.
 */
public class FastJsonUtil {
    private static  final SerializeConfig config;
    static {
        config = new SerializeConfig();
        config.put(java.util.Date.class,new JSONLibDataFormatSerializer());
        config.put(java.sql.Date.class,new JSONLibDataFormatSerializer());
    }
    private static final SerializerFeature[] features = {
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.WriteNullNumberAsZero,
            SerializerFeature.WriteNullStringAsEmpty
    };

    public static String toJSONString(Object object){
        return JSON.toJSONString(object,config,features);
    }

    public static String toJSONNoFeatures(Object object){
        return JSON.toJSONString(object,config);
    }

    public static Object toBean(String text){
        return JSON.parse(text);
    }

    public static <T> T toBean(String text,Class<T> clazz){
        return JSON.parseObject(text,clazz);
    }

    public static <T> Object[] toArray(String text){
        return toArray(text,null);
    }

    public static <T> Object[] toArray(String text,Class<T> clazz){
        return JSON.parseArray(text,clazz).toArray();
    }

    public static <T> List<T> toList(String text, Class<T> clazz){
        return JSON.parseArray(text,clazz);
    }

    public static Object beanToJson(KeyValue keyValue){
        String textJson = JSON.toJSONString(keyValue);
        Object objectJson = JSON.parse(textJson);
        return objectJson;
    }

    public static Object textToJson(String text){
        Object objectJson = JSON.parse(text);
        return objectJson;
    }

    public static Map<String ,Object> stringToMap(String text){
        return JSONObject.parseObject(text);
    }

    public static String MapToString(Map<String,Object> map){
        return JSONObject.toJSONString(map);
    }

}
