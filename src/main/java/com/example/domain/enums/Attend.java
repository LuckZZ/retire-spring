package com.example.domain.enums;

/**
 * 是否参加活动
 *
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
public enum Attend {
    yes("参加"),no("不参加");
    private String name;

    private Attend(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
