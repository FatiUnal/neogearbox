package com.example.blogv1.dto;

import java.util.List;

public class PostSmallDto {
    private int id;
    private String title;
    private String titleContent;
    private String content;
    private String coverImage;
    private List<String> images;
    private String categoryName;


    public PostSmallDto(int id, String title, String titleContent, String content, String coverImage, List<String> images, String categoryName) {
        this.id = id;
        this.title = title;
        this.titleContent = titleContent;
        this.content = content;
        this.coverImage = coverImage;
        this.images = images;
        this.categoryName = categoryName;
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

    public String getTitleContent() {
        return titleContent;
    }

    public void setTitleContent(String titleContent) {
        this.titleContent = titleContent;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
