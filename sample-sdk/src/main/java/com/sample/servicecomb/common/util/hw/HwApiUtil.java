package com.sample.servicecomb.common.util.hw;

import com.sample.servicecomb.common.configuration.FrsConfigurationProperties;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * @ClassName HwApiUtil
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/3/15 16:36
 */
public class HwApiUtil {
    private static final String PROTOCOL = "https://";
    private static final String DELIMITER = "/";
    private static final String GET_DELIMITER = "?";
    private static final String GET_PLACEHOLDER = "&";
    private static final String EQUAL_SIGN = "=";


    public static String getFaceUrl(FrsConfigurationProperties properties,HttpMethod method, Map<String,String> map, String... args){
        StringBuffer httpUrl = new StringBuffer(PROTOCOL)
                .append(properties.getEndPoint())
                .append(DELIMITER)
                .append(properties.getApiVersion())
                .append(DELIMITER)
                .append(properties.getProjectId())
                .append(DELIMITER);
        switch (method){
            case POST:
                for (int i = 0; i < args.length; i++){
                    if(i == (args.length - 1)){
                        httpUrl.append(args[i]);
                    }else {
                        httpUrl.append(args[i]).append(DELIMITER);
                    }
                }
                break;
            case GET:
                if(map == null){
                    for (int i = 0; i < args.length ; i++){
                        if(i == (args.length - 1)){
                            httpUrl.append(args[i]);
                        }else {
                            httpUrl.append(args[i]).append(DELIMITER);
                        }
                    }
                }else {
                    httpUrl.append(args[0]).append(DELIMITER);
                    map.forEach((key, value) -> httpUrl.append(key).append(EQUAL_SIGN).append(value).append(GET_PLACEHOLDER));
                    httpUrl.deleteCharAt(httpUrl.lastIndexOf(GET_PLACEHOLDER));
                }
                break;
            case PUT:
                for (int i = 0; i < args.length; i++){
                    if(i == (args.length - 1)){
                        httpUrl.append(args[i]);
                    }else {
                        httpUrl.append(args[i]).append(DELIMITER);
                    }
                }
                break;
            case DELETE:
                if(map == null){
                    for (int i = 0; i < args.length ; i++){
                        if(i == (args.length - 1)){
                            httpUrl.append(args[i]);
                        }else {
                            httpUrl.append(args[i]).append(DELIMITER);
                        }
                    }
                }else {
                    httpUrl.append(args[0]).append(GET_DELIMITER);
                    map.forEach((key, value) -> httpUrl.append(key).append(EQUAL_SIGN).append(value).append(GET_PLACEHOLDER));
                    httpUrl.deleteCharAt(httpUrl.lastIndexOf(GET_PLACEHOLDER));
                }
                break;
            default:
                break;
        }
        return httpUrl.toString();
    }

}
