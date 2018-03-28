package com.example.domain.enums;

public enum Exist {
    yes("当前人员"),no("去世人员");
    private String name;

    private Exist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
