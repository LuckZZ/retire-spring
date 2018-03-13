package com.example.domain.enums;

/**
 * 用户身份枚举
 */
public enum LoginType {

    Admin("管理员"),Zuzhang("组长");

    private String typeName;

    LoginType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
