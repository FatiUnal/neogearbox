package com.example.blogv1.entity.post;

public enum PostStatus {
    ACTIVE("ACTIVE"), INACTIVE("INACTIVE"),PAST("PAST");
    private String value;
    private PostStatus(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
