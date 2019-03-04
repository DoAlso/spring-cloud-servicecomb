package com.sample.servicecomb.scca.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 环境的所属参数
 * <p>
 * Created by 程序猿DD/翟永超 on 2018/6/16.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
public class EnvParamDto {

    private Long id;

    /**
     * 配置key
     **/
    @JsonProperty("pKey")
    private String pKey;
    /**
     * 配置value
     **/
    @JsonProperty("pValue")
    private String pValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getpKey() {
        return pKey;
    }

    public void setpKey(String pKey) {
        this.pKey = pKey;
    }

    public String getpValue() {
        return pValue;
    }

    public void setpValue(String pValue) {
        this.pValue = pValue;
    }
}
