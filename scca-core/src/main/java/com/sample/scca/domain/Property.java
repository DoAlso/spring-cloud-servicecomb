package com.sample.scca.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * 详细配置表
 * <p>
 * <p>
 * Created by 程序猿DD/翟永超 on 2018/6/20.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Entity
public class Property {

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
     */
    @OneToOne
    private Env env;

    /**
     * 所属项目
     */
    @OneToOne
    private Project project;

    /**
     * 所属版本
     */
    @OneToOne
    private Label label;


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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
}
