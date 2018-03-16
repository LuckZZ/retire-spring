package com.example.domain.enums;

public enum CanLogin {
    yes("允许登陆"),no("禁止登陆");
    private String name;

    private CanLogin(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
