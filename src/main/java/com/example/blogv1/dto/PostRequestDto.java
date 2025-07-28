package com.example.blogv1.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.Column;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")  // Gelen JSON'dan 'type' değerine göre doğru sınıf seçilecek.
@JsonSubTypes({
        @JsonSubTypes.Type(value = KwRequestDto.class, name = "kw")
})
public class PostRequestDto {
    private String title;
    private String titleEng;
    private String titleContent;
    private String titleContentEng;

    @Column(length = 5000)  // Maksimum 5000 karakter
    private String content;
    private String contentEng;
    private int categoryId;

    public PostRequestDto(String title, String titleEng, String titleContent, String titleContentEng, String content, String contentEng, int categoryId) {
        this.title = title;
        this.titleEng = titleEng;
        this.titleContent = titleContent;
        this.titleContentEng = titleContentEng;
        this.content = content;
        this.contentEng = contentEng;
        this.categoryId = categoryId;
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

    public String getTitleContent() {
        return titleContent;
    }

    public void setTitleContent(String titleContent) {
        this.titleContent = titleContent;
    }

    public int getCategoryName() {
        return categoryId;
    }

    public void setCategoryName(int categoryId) {
        this.categoryId = categoryId;
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
