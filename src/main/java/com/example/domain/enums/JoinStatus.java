package com.example.domain.enums;

/**
 * Create by : Zhangxuemeng on 2018/5/26
 * Csdn blog：https://blog.csdn.net/Luck_ZZ
 */
public enum JoinStatus {
    draft("草稿"),ultima("最终");
    private String name;

    private JoinStatus(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
