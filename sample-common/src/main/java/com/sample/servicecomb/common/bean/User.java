package com.sample.servicecomb.common.bean;

/**
 * @ClassName User
 * @Description TODO
 * @Author Administrator
 * @DATE 2019/1/21 17:22
 */
public class User {
    private Long id;
    private String userName;
    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
