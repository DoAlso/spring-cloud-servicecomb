package com.sample.servicecomb.common.smn;

import com.sample.servicecomb.common.configuration.SmnConfigurationProperties;
import com.smn.client.AkskSmnClient;
import com.smn.client.SmnClient;
import com.smn.request.sms.SmsPublishRequest;
import com.smn.response.sms.SmsPublishResponse;

/**
 * @ClassName SmnClientUtil
 * @Author huyaxi
 * @DATE 2019/4/29 9:41
 */
public class SmnClientUtil {
    private static final Integer HTTP_SUCCESS = 200;
    private SmnClient smnClient;
    private SmnConfigurationProperties properties;

    public SmnClient getInstance(){
        if (smnClient == null) {
            synchronized (SmnClientUtil.class) {
                if (smnClient == null) {
                    smnClient = createSmnClient();
                }
            }
        }
        return smnClient;
    }

    /**
     * 发送短信
     * @param phone
     * @param content
     * @return
     * @throws Exception
     */
    public boolean sendMessage(String phone,String content) throws Exception{
        SmsPublishRequest smnRequest = new SmsPublishRequest();
        smnRequest.setEndpoint(phone)
                .setMessage(content)
                .setSignId(properties.getSignId());
        SmsPublishResponse publishResponse = smnClient.sendRequest(smnRequest);
        if(HTTP_SUCCESS.equals(publishResponse.getHttpCode())){
            return true;
        }
        return false;
    }


    private SmnClient createSmnClient() {
        SmnClient smnClient = new AkskSmnClient(properties.getAk(),
                properties.getSk(),properties.getRegion());
        return smnClient;
    }

    public void setProperties(SmnConfigurationProperties properties) {
        this.properties = properties;
    }
}
