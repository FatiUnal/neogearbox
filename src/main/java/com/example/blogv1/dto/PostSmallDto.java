package com.example.blogv1.dto;

import java.util.List;

public class PostSmallDto {
    private int id;
    private String title;
    private String content;
    private String coverImage;
    private List<String> images;
    private String price;
    private String sehir;
    private String ilce;

    public PostSmallDto(int id, String title, String content, String coverImage, List<String> images, String price, String sehir, String ilce) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.coverImage = coverImage;
        this.images = images;
        this.price = price;
        this.sehir = sehir;
        this.ilce = ilce;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
