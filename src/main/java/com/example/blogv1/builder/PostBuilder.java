package com.example.blogv1.builder;

import com.example.blogv1.dto.PostSmallDto;
import com.example.blogv1.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostBuilder {

    public PostSmallDto postToPostSmallDto(Post post) {
        return new PostSmallDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCoverImage()
        );
    }

}
