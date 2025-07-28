package com.example.blogv1.dto;

import java.util.List;

public class PostSmallDto {
    private int id;
    private String title;
    private String titleEng;
    private String titleContent;
    private String titleContentEng;
    private String content;
    private String contentEng;
    private String coverImage;
    private List<String> images;
    private String categoryName;


    public PostSmallDto(int id, String title, String titleEng, String titleContent, String titleContentEng, String content, String contentEng, String coverImage, List<String> images, String categoryName) {
        this.id = id;
        this.title = title;
        this.titleEng = titleEng;
        this.titleContent = titleContent;
        this.titleContentEng = titleContentEng;
        this.content = content;
        this.contentEng = contentEng;
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

    public String getTitleEng() {
        return titleEng;
    }

    public void setTitleEng(String titleEng) {
        this.titleEng = titleEng;
    }

    public String getTitleContentEng() {
        return titleContentEng;
    }

    public void setTitleContentEng(String titleContentEng) {
        this.titleContentEng = titleContentEng;
    }

    public String getContentEng() {
        return contentEng;
    }

    public void setContentEng(String contentEng) {
        this.contentEng = contentEng;
    }
}
