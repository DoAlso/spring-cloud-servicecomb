package com.sample.servicecomb.scca.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 需要加密的key
 * <p>
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
public class EncryptKeyDto {

    private Long id;

    /**
     * 需要加密的key
     **/
    @JsonProperty("eKey")
    private String eKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String geteKey() {
        return eKey;
    }

    public void seteKey(String eKey) {
        this.eKey = eKey;
    }
}
