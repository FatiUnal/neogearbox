package com.example.blogv1.dto;

import java.util.List;

public class PostSmallDto {
    private int id;
    private String title;
    private String content;
    private String coverImage;
    private List<String> images;
    private String price;

    public PostSmallDto(int id, String title, String content, String coverImage, List<String> images, String price) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.coverImage = coverImage;
        this.images = images;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public List<String> getImages() {
        return images;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
