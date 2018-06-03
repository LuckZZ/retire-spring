package com.example.domain.enums;

/**
 * Create by : Zhangxuemeng on 2018/5/26
 * Csdn blog：https://blog.csdn.net/Luck_ZZ
 */
public enum JoinStatus {
    draft("已保存"),ultima("已报名");
    private String name;

    private JoinStatus(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
