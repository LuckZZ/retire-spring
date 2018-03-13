package com.example.domain.enums;

public enum LoginType {
    Admin(1,"管理员"),Zuzhang(2,"组长"),Zuyuan(3,"组员");

    private int typeId;
    private String typeName;

    LoginType(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }
}
