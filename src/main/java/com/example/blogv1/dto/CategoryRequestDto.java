package com.example.blogv1.dto;

public class CategoryRequestDto {
    private String categoryName;

    public CategoryRequestDto(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
