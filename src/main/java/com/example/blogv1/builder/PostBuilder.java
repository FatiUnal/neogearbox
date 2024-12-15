package com.example.blogv1.builder;

import com.example.blogv1.dto.PostSmallDto;
import com.example.blogv1.entity.post.Image;
import com.example.blogv1.entity.post.Post;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PostBuilder {

    public PostSmallDto postToPostSmallDto(Post post) {
        System.out.println("1");
        String coverImage = "";
        System.out.println("2");

        List<String> images = new ArrayList<>();
        System.out.println("3");


        if (post.getCoverImage().getFilename() != null) {
            System.out.println("4");
            coverImage = post.getCoverImage().getFilename();
            System.out.println("5");

        }
        System.out.println("6");


        if (post.getImages() != null) {
            System.out.println("7");

            post.getImages().forEach(image -> images.add(image.getFilename()));
            System.out.println("8");

        }

        return new PostSmallDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                coverImage,
                images
        );
    }
}
