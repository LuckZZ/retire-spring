package com.example.domain.enums;

public enum Role {
    admin("管理员"), grouper("组长");
    private String name;

    private Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
