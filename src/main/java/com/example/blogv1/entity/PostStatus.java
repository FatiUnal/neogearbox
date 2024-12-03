package com.example.blogv1.entity;

public enum PostStatus {
    ACTIVE("active"), INACTIVE("inactive"),PAST("past");
    private String value;
    private PostStatus(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
