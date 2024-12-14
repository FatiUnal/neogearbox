package com.example.blogv1.builder;

import com.example.blogv1.dto.PostSmallDto;
import com.example.blogv1.entity.post.Image;
import com.example.blogv1.entity.post.Post;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class PostBuilder {

    public PostSmallDto postToPostSmallDto(Post post) {
        return new PostSmallDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCoverImage() != null ? post.getCoverImage().getFilename() : "",
                post.getImages() != null ? post.getImages().stream().map(Image::getFilename).toList() : Collections.emptyList()
        );
    }
}
