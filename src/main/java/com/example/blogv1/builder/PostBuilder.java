package com.example.blogv1.builder;

import com.example.blogv1.dto.PostSmallDto;
import com.example.blogv1.entity.post.HouseListing;
import com.example.blogv1.entity.post.Image;
import com.example.blogv1.entity.post.Post;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
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
                post.getContent(),
                coverImage,
                images,
                post.getPostDetails().getFiyat(),
                post.getPostDetails().getSehir(),
                post.getPostDetails().getIlce(),
                ((HouseListing)post.getPostDetails()).getOdaSayisi()
        );
    }
}
