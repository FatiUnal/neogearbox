package com.example.blogv1.entity;

public enum EstateType {
    ARAZI("arazi"),GAYRIMENKUL("house");
    private String type;
    private EstateType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
}
