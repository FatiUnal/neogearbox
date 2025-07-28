package com.example.blogv1.dto;

public class CategoryRequestDto {
    private String categoryName;
    private String categoryNameEn;

    public CategoryRequestDto(String categoryName, String categoryNameEn) {
        this.categoryName = categoryName;
        this.categoryNameEn = categoryNameEn;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryNameEn() {
        return categoryNameEn;
    }

    public void setCategoryNameEn(String categoryNameEn) {
        this.categoryNameEn = categoryNameEn;
    }
}
