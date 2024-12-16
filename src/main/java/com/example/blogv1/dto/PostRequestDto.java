package com.example.blogv1.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.Column;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")  // Gelen JSON'dan 'type' değerine göre doğru sınıf seçilecek.
@JsonSubTypes({
        @JsonSubTypes.Type(value = HouseRequestDto.class, name = "house"),
        @JsonSubTypes.Type(value = LandRequestDto.class, name = "land")
})
public class PostRequestDto {
    private String title;

    @Column(length = 5000)  // Maksimum 5000 karakter
    private String content;
    private String type;
    private String sehir;
    private String ilce;

    public PostRequestDto() {}

    public PostRequestDto(String title, String content, String type, String sehir, String ilce) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.sehir = sehir;
        this.ilce = ilce;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSehir() {
        return sehir;
    }

    public void setSehir(String sehir) {
        this.sehir = sehir;
    }

    public String getIlce() {
        return ilce;
    }

    public void setIlce(String ilce) {
        this.ilce = ilce;
    }
}
