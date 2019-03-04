package com.sample.servicecomb.scca.rest.dto;


/**
 * 标签（灰度配置）
 *
 * @author 翟永超
 * @create 2018/4/24.
 * @blog http://blog.didispace.com
 */
public class LabelDto {

    private Long id;

    /**
     * 标签名
     **/
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
