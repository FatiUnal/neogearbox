package com.example.blogv1.dto;

public class KwRequestDto extends PostRequestDto{


    public KwRequestDto(String title, String titleEn, String titleContent, String titleContentEn, String content, String contentEn, int categoryId) {
        super(title,titleEn, titleContent,titleContentEn, content,contentEn, categoryId);
    }

}
