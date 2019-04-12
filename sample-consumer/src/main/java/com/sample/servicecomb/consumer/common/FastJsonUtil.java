package com.sample.servicecomb.consumer.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 胡亚曦 on 2016/12/8.
 */
public class FastJsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FastJsonUtil.class);
    private static final SerializeConfig config;

    static {
        config = new SerializeConfig();
        config.put(Date.class, new JSONLibDataFormatSerializer());
        config.put(java.sql.Date.class, new JSONLibDataFormatSerializer());
    }

    private static final SerializerFeature[] features = {
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.WriteNullNumberAsZero,
            SerializerFeature.WriteNullStringAsEmpty
    };

    public static String toJSONString(Object object) {
        return JSON.toJSONString(object, config, features);
    }

    public static String toJSONNoFeatures(Object object) {
        return JSON.toJSONString(object, config);
    }

    public static Object toBean(String text) {
        return JSON.parse(text);
    }

    public static <T> T toBean(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    public static <T> Object[] toArray(String text) {
        return toArray(text, null);
    }

    public static <T> Object[] toArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz).toArray();
    }

    public static <T> List<T> toList(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

    public static Object beanToJson(KeyValue keyValue) {
        String textJson = JSON.toJSONString(keyValue);
        Object objectJson = JSON.parse(textJson);
        return objectJson;
    }

    public static Object textToJson(String text) {
        Object objectJson = JSON.parse(text);
        return objectJson;
    }

    public static Map<String, Object> stringToMap(String text) {
        return JSONObject.parseObject(text);
    }

    public static String MapToString(Map<String, Object> map) {
        return JSONObject.toJSONString(map);
    }

    public static <T> T parse(Object object, Class<T> tClass) {
        return FastJsonUtil.toBean(JSONObject.toJSONString(object), tClass);
    }

    public static <T> T parse(Object object, Type type) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");
        try {
            return parse(JSONObject.toJSONString(object), type);
        } catch (JSONException var4) {
            LOGGER.error("父类转子类异常：{},异常执行时间：{},被转换类名称：{},目标转换类名称：{}", new Object[]{var4.getMessage(), sdf.format(new Date()), object.getClass().getTypeName(), type.getTypeName()});
            return null;
        }
    }

}
