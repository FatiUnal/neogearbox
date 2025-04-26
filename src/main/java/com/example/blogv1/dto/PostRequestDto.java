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
    private String titleContent;

    @Column(length = 5000)  // Maksimum 5000 karakter
    private String content;
    private int categoryId;

    public PostRequestDto(String title, String titleContent, String content, int categoryId) {
        this.title = title;
        this.titleContent = titleContent;
        this.content = content;
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
}
