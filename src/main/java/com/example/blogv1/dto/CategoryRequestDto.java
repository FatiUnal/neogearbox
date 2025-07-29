package com.example.blogv1.dto;

public class CategoryRequestDto {
    private String categoryName;
    private String categoryNameEng;

    public CategoryRequestDto(String categoryName, String categoryNameEng) {
        this.categoryName = categoryName;
        this.categoryNameEng = categoryNameEng;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryNameEng() {
        return categoryNameEng;
    }

    public void setCategoryNameEng(String categoryNameEng) {
        this.categoryNameEng = categoryNameEng;
    }
}
