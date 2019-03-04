package com.sample.servicecomb.scca.rest.dto;


import com.sample.scca.service.ConfigServerInfo;

import java.util.List;

/**
 * Dashboard上使用的环境概览信息
 * <p>
 * Created by 程序猿DD/翟永超 on 2018/5/16.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
public class DashboardEnvDto {

    private Long id;

    /**
     * 环境名
     **/
    private String name;

    /**
     * 配置服务器
     */
    private List<ConfigServerInfo> configServers;

    /**
     * 项目数量
     */
    private Integer projects;

    /**
     * 环境参数数量
     */
    private Integer params;


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

    public List<ConfigServerInfo> getConfigServers() {
        return configServers;
    }

    public void setConfigServers(List<ConfigServerInfo> configServers) {
        this.configServers = configServers;
    }

    public Integer getProjects() {
        return projects;
    }

    public void setProjects(Integer projects) {
        this.projects = projects;
    }

    public Integer getParams() {
        return params;
    }

    public void setParams(Integer params) {
        this.params = params;
    }
}
