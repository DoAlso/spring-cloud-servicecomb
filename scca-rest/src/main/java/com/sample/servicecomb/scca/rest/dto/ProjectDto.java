package com.sample.servicecomb.scca.rest.dto;


import java.util.ArrayList;
import java.util.List;

/**
 * 项目
 * <p>
 * Created by 程序猿DD/翟永超 on 2018/5/21.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
public class ProjectDto {

    private Long id;

    /**
     * 项目名
     */
    private String name;

    /**
     * 部署的环境列表
     */
    private List<EnvDto> envs = new ArrayList<>();

    /**
     * 配置便签列表（灰度用）
     */
    private List<LabelDto> labels = new ArrayList<>();


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

    public List<EnvDto> getEnvs() {
        return envs;
    }

    public void setEnvs(List<EnvDto> envs) {
        this.envs = envs;
    }

    public List<LabelDto> getLabels() {
        return labels;
    }

    public void setLabels(List<LabelDto> labels) {
        this.labels = labels;
    }
}
