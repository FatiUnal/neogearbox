package com.example.blogv1.repository;

import com.example.blogv1.entity.EstateType;
import com.example.blogv1.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByStatus(String status);
    List<Post> findAllByPostDetails_Estate(EstateType estate);
}
