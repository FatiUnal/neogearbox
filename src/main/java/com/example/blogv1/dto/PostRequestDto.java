package com.example.blogv1.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.Column;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")  // Gelen JSON'dan 'type' değerine göre doğru sınıf seçilecek.
@JsonSubTypes({
        @JsonSubTypes.Type(value = BakeRequestDto.class, name = "bake")
})
public class PostRequestDto {
    private String title;

    @Column(length = 5000)  // Maksimum 5000 karakter
    private String content;

    public PostRequestDto() {}

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
