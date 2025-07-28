package com.example.blogv1.builder;

import com.example.blogv1.dto.PostSmallDto;
import com.example.blogv1.entity.post.Post;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostBuilder {

    public PostSmallDto postToPostSmallDto(Post post) {

        String coverImage = "";
        List<String> images = new ArrayList<>();


        if (post.getCoverImage() != null) {
            coverImage = post.getCoverImage().getFilename();
        }else
            coverImage="";



        if (post.getImages() != null) {
            post.getImages().forEach(image -> images.add(image.getFilename()));
        }

        return new PostSmallDto(
                post.getId(),
                post.getTitle(),
                post.getTitleEng() != null ? post.getTitleEng() : null,
                post.getTitleContent(),
                post.getTitleContentEng() != null ? post.getTitleContentEng() : null,
                post.getContent(),
                post.getContentEng() != null ? post.getContentEng() : null,
                coverImage,
                images,
                post.getPostDetails().getCategory().getName()
        );
    }
}
