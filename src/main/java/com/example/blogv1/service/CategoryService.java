package com.example.blogv1.service;

import com.example.blogv1.dto.CategoryRequestDto;
import com.example.blogv1.entity.Admin;
import com.example.blogv1.entity.post.Category;
import com.example.blogv1.exception.BadRequestException;
import com.example.blogv1.exception.ConflictException;
import com.example.blogv1.exception.NotFoundException;
import com.example.blogv1.repository.CategoryRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final AdminService adminService;

    public CategoryService(CategoryRepository categoryRepository, AdminService adminService) {
        this.categoryRepository = categoryRepository;
        this.adminService = adminService;
    }

    public Category create(CategoryRequestDto categoryRequestDto){
        if (categoryRepository.existsByName(categoryRequestDto.getCategoryName()))
            throw new ConflictException("Category name already exists");

        if (categoryRequestDto.getCategoryName().isEmpty())
            throw new BadRequestException("Category name is empty");

        Category category = new Category(categoryRequestDto.getCategoryName());
        return categoryRepository.save(category);
    }

    public Category update(Integer id, CategoryRequestDto categoryRequestDto){
        if (categoryRequestDto.getCategoryName().isEmpty())
            throw new BadRequestException("Category name is empty");

        if (categoryRepository.existsByName(categoryRequestDto.getCategoryName()))
            throw new ConflictException("Category name already exists");

        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
        category.setName(categoryRequestDto.getCategoryName());

        return categoryRepository.save(category);
    }

    public List<Category> getAll(){
        return categoryRepository.findAll();
    }

    public Category findById(int categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found"));
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
}
