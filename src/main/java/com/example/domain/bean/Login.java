package com.example.domain.bean;

import com.example.domain.enums.Role;

/**
 * Create by : Zhangxuemeng
 */
public class Login {
    private String jobNum;
    private String name;
    private Role role;

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
}
