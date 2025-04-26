package com.example.blogv1.controller;

import com.example.blogv1.dto.CategoryRequestDto;
import com.example.blogv1.entity.post.Category;
import com.example.blogv1.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        Category createdCategory = categoryService.create(categoryRequestDto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Category> updateCategory(
            @RequestParam Integer id,
            @RequestBody CategoryRequestDto categoryRequestDto) {
        Category updatedCategory = categoryService.update(id, categoryRequestDto);
        return ResponseEntity.ok(updatedCategory);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<Category> getCategoryById(@RequestParam Integer id) {
        return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
    }

}
