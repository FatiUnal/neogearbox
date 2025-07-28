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
    private String titleEn;
    private String titleContent;
    private String titleContentEn;

    @Column(length = 5000)  // Maksimum 5000 karakter
    private String content;
    private String contentEn;
    private int categoryId;

    public PostRequestDto(String title, String titleEn, String titleContent, String titleContentEn, String content, String contentEn, int categoryId) {
        this.title = title;
        this.titleEn = titleEn;
        this.titleContent = titleContent;
        this.titleContentEn = titleContentEn;
        this.content = content;
        this.contentEn = contentEn;
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
