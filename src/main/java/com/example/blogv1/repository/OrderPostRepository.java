package com.example.blogv1.repository;

import com.example.blogv1.entity.post.OrderPost;
import com.example.blogv1.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderPostRepository extends JpaRepository<OrderPost, Integer> {

    boolean existsByOrderId(int order);
    Optional<OrderPost> findByPost(Post post);
    List<OrderPost> findAllByOrderByOrderIdAsc();

}
