package com.sample.scca.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.scca.domain.EncryptKeyRepo;
import com.sample.scca.domain.Env;
import com.sample.scca.domain.EnvParamRepo;
import com.sample.scca.domain.EnvRepo;
import com.sample.scca.service.BaseOptService;
import com.sample.scca.service.ConfigServerInfo;
import com.sample.scca.service.UrlMakerService;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Service
public class BaseOptServiceImpl implements BaseOptService {
    private static Logger log = LoggerFactory.getLogger(BaseOptServiceImpl.class);
    private String encryptPath = "/encrypt";
    private String decryptPath = "/decrypt";

    private OkHttpClient okHttpClient = buildOkHttpClient();

    @Autowired
    protected EnvParamRepo envParamRepo;
    @Autowired
    protected EncryptKeyRepo encryptKeyRepo;
    @Autowired
    protected EnvRepo environmentRepo;

    @Autowired
    protected UrlMakerService urlMakerService;

    @Override
    public String encrypt(String originValue, Env env) throws Exception{
        return callTextPlain(urlMakerService.configServerBaseUrl(env.getName()) + encryptPath, originValue);
    }

    @Override
    public String decrypt(String originValue, Env env) throws Exception{
        return callTextPlain(urlMakerService.configServerBaseUrl(env.getName()) + decryptPath, originValue);
    }


    @Override
    public List<ConfigServerInfo> configServerInfo(Env env) throws Exception{
        List<ConfigServerInfo> result = new ArrayList<>();
        for(String url : urlMakerService.allConfigServerBaseUrl(env.getName())) {
            ConfigServerInfo configServerInfo = new ConfigServerInfo();
            configServerInfo.setUrl(url);

            // 获取该配置中心的加密状态
            String response = callGet(url + "/encrypt/status");
            log.info("response : " + response);
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> rMap = mapper.readValue(response, Map.class);
            configServerInfo.setEncryptStatus(rMap.get("status"));

            result.add(configServerInfo);
        }
        return result;
    }

    @Override
    public Environment getProperties(String application, String envName, String label) throws Exception{
        String url = urlMakerService.propertiesLoadUrl(application, envName, label);
        return callGetProperties(url);
    }

    private String callGet(String url) throws Exception{
        log.info("call get : " + url);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        return responseBody.string();
    }


    private String callTextPlain(String url, String value) throws Exception{
        log.info("call text plain : " + url);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "text/plain")
                .post(RequestBody.create(MediaType.parse("text/plain"), value.getBytes()))
                .build();

        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();
        return responseBody.string();
    }


    private Environment callGetProperties(String url) throws Exception{
        log.info("call get properties : " + url);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        ResponseBody responseBody = response.body();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(responseBody.string(), Environment.class);
    }

    private OkHttpClient buildOkHttpClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        return client;
    }

}
