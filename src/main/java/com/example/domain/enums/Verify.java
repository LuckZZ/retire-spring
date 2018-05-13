package com.example.domain.enums;

/**
 * 是否参加活动
 */
public enum Verify {
    no("未验证"),yes("已验证");
    private String name;

    private Verify(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
