package com.example.blogv1.entity.post;

public enum EstateType {
    LAND("LAND"),HOUSE("HOUSE");
    private String type;
    private EstateType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
}
