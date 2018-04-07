package com.example.domain.enums;

/**
 * 活动状态
 */
public enum ActivityStatus {
    draft("草稿"),open("打开报名"),close("关闭报名");
    private String name;

    private ActivityStatus(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
