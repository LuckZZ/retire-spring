package com.example.domain.bean;

import com.example.domain.entity.Group;
import com.example.domain.enums.Role;

/**
 * Create by : Zhangxuemeng
 */
public class Login {
    private Integer id;
    private String jobNum;
    private String name;
    private String imgUrl;
    private Role role;
    private Group group;

    public Login() {
    }

    public Login(Integer id, String jobNum, String name, String imgUrl, Role role) {
        this.id = id;
        this.jobNum = jobNum;
        this.name = name;
        this.imgUrl = imgUrl;
        this.role = role;
    }

    public Login(Integer id, String jobNum, String name, String imgUrl, Role role, Group group) {
        this.id = id;
        this.jobNum = jobNum;
        this.name = name;
        this.imgUrl = imgUrl;
        this.role = role;
        this.group = group;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
