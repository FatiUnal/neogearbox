package com.example.blogv1.dto;

public class BakeRequestDto extends PostRequestDto{

    private String bake;

    public BakeRequestDto(String title, String content, String bake) {
        super(title, content);
        this.bake = bake;
    }
    public String getBake() {
        return bake;
    }
    public void setBake(String bake) {
        this.bake = bake;
    }
}
