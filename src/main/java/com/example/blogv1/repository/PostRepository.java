package com.example.blogv1.repository;

import com.example.blogv1.entity.post.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT p FROM Post p WHERE p.postDetails.category.id = :categoryId")
    List<Post> findByCategoryId(@Param("categoryId") int categoryId, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.postDetails.category.linkName = :linkName")
    List<Post> findByCategoryLinkName(@Param("linkName") String linkName, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.postDetails.category.id = :categoryId")
    List<Post> findByCategoryId(@Param("categoryId") int categoryId);

    List<Post> findByTitleContainingIgnoreCase(String title);
    List<Post> findByTitleEngContainingIgnoreCase(String titleEng);

}
