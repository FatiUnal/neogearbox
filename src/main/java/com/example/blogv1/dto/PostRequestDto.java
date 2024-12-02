package com.example.blogv1.dto;

import jakarta.persistence.Column;

public class PostRequestDto {
    private String title;
    @Column(length = 5000)  // Maksimum 5000 karakter
    private String content;

    public PostRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
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
}
