package com.example.blogv1.dto;

public class PostImagerOrderRequestDto {
    private int imageId;
    private int orderId;

    public PostImagerOrderRequestDto(int imageId, int orderId) {
        this.imageId = imageId;
        this.orderId = orderId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
