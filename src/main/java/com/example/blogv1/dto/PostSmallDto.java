package com.example.blogv1.dto;

import java.util.List;

public class PostSmallDto {
    private int id;
    private String title;
    private String titleEn;
    private String titleContent;
    private String titleContentEn;
    private String content;
    private String contentEn;
    private String coverImage;
    private List<String> images;
    private String categoryName;


    public PostSmallDto(int id, String title, String titleEn, String titleContent, String titleContentEn, String content, String contentEn, String coverImage, List<String> images, String categoryName) {
        this.id = id;
        this.title = title;
        this.titleEn = titleEn;
        this.titleContent = titleContent;
        this.titleContentEn = titleContentEn;
        this.content = content;
        this.contentEn = contentEn;
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

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitleContentEn() {
        return titleContentEn;
    }

    public void setTitleContentEn(String titleContentEn) {
        this.titleContentEn = titleContentEn;
    }

    public String getContentEn() {
        return contentEn;
    }

    public void setContentEn(String contentEn) {
        this.contentEn = contentEn;
    }
}
