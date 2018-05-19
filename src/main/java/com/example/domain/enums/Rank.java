package com.example.domain.enums;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
public enum Rank {
    grouper("组长"),user("组员");
    private String name;

    private Rank(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
