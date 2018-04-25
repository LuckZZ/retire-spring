package com.example.domain.enums;

/**
 * Create by : Zhangxuemeng
 */
public enum SearchType {
    all("所有"),search("普通搜索"),searchSuper("高级搜索");

    private String name;

    private SearchType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
