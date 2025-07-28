package com.example.blogv1.dto;

public class KwRequestDto extends PostRequestDto{


    public KwRequestDto(String title, String titleEng, String titleContent, String titleContentEng, String content, String contentEng, int categoryId) {
        super(title,titleEng, titleContent,titleContentEng, content,contentEng, categoryId);
    }

}
