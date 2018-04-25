package com.example.domain.bean;

/**
 * type:
 * 1：按工号查询
 * 2：按姓名查询
 * Create by : Zhangxuemeng
 */
public class UserCommSearch {
    private int type;
    private String value;

    public UserCommSearch() {
    }

    public UserCommSearch(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}