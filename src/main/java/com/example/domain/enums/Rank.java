package com.example.domain.enums;

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
