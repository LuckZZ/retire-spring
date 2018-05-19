package com.example.domain.enums;

/**
 * 活动状态
 *
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
public enum ActivityStatus {
    draft("草稿"),open("打开"),close("关闭");
    private String name;

    private ActivityStatus(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
