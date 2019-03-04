package com.sample.scca.domain;

import javax.persistence.*;
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

@Entity
public class Project {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 项目名
     */
    @Column(unique = true)
    private String name;

    /**
     * 该项目有哪些环境的配置
     */
    @ManyToMany
    private List<Env> envs = new ArrayList<>();

    /**
     * 该项目的配置有哪些版本
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<Label> labels = new ArrayList<>();


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

    public List<Env> getEnvs() {
        return envs;
    }

    public void setEnvs(List<Env> envs) {
        this.envs = envs;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }
}
