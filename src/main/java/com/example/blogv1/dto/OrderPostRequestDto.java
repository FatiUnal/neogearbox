package com.example.blogv1.dto;

public class OrderPostRequestDto {
    private int postId;
    private int order;

    public OrderPostRequestDto(int postId, int order) {
        this.postId = postId;
        this.order = order;
    }

    public int getPostId() {
        return postId;
    }

    public int getOrder() {
        return order;
    }
}
