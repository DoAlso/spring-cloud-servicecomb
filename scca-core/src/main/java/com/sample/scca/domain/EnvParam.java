package com.sample.scca.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * 环境属性
 *
 * @author 翟永超
 * @create 2018/4/24.
 * @blog http://blog.didispace.com
 */
@Entity
public class EnvParam {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 配置key
     **/
    private String pKey;
    /**
     * 配置value
     **/
    private String pValue;

    /**
     * 所属环境
     **/
    @ManyToOne
    private Env env;


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

    public Env getEnv() {
        return env;
    }

    public void setEnv(Env env) {
        this.env = env;
    }
}
