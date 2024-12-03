package com.example.blogv1.dto;

public class PostSmallDto {
    private int id;
    private String title;
    private String content;
    private String coverImage;

    public PostSmallDto(int id, String title, String content, String coverImage) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.coverImage = coverImage;
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
}
