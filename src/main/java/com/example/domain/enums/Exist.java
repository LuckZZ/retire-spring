package com.example.domain.enums;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
public enum Exist {
    yes("退休人员"),no("离世人员");
    private String name;

    private Exist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
