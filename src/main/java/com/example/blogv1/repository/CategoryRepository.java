package com.example.blogv1.repository;

import com.example.blogv1.entity.post.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    boolean existsByName(String name);
    boolean existsByLinkName(String linkName);

    List<Category> findByLinkNameIsNull();
    Optional<Category> findByLinkName(String linkName);
}
