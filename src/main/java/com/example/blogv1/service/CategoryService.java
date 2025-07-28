package com.example.blogv1.service;

import com.example.blogv1.dto.CategoryRequestDto;
import com.example.blogv1.entity.post.Category;
import com.example.blogv1.exception.BadRequestException;
import com.example.blogv1.exception.ConflictException;
import com.example.blogv1.exception.NotFoundException;
import com.example.blogv1.repository.CategoryRepository;
import com.example.blogv1.repository.PostRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;


    public CategoryService(CategoryRepository categoryRepository, PostRepository postRepository) {
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
    }

    public Category create(CategoryRequestDto categoryRequestDto){
        if (categoryRepository.existsByName(categoryRequestDto.getCategoryName()))
            throw new ConflictException("Category name already exists");

        if (categoryRequestDto.getCategoryName().isEmpty())
            throw new BadRequestException("Category name is empty");

        Category category = new Category(categoryRequestDto.getCategoryName(),categoryRequestDto.getCategoryNameEn());
        return categoryRepository.save(category);
    }

    public Category update(Integer id, CategoryRequestDto categoryRequestDto){
        if (categoryRequestDto.getCategoryName().isEmpty())
            throw new BadRequestException("Category name is empty");

        if (categoryRepository.existsByName(categoryRequestDto.getCategoryName()))
            throw new ConflictException("Category name already exists");

        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
        category.setName(categoryRequestDto.getCategoryName());
        category.setNameEn(categoryRequestDto.getCategoryNameEn());

        return categoryRepository.save(category);
    }

    public List<Category> getAll(){
        return categoryRepository.findAll();
    }

    public Category findById(int categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found"));
    }

    public Category findByLinkName(String linkName) {
        System.out.println("linkName: " + linkName);
        return categoryRepository.findByLinkName(linkName).orElseThrow(() -> new NotFoundException("Category not found"));
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public String deleteCategoryById(Category category) {
        categoryRepository.delete(category);
        return "success";
    }

    public boolean hasPostByCategoryId(int categoryId) {
        if (postRepository.findByCategoryId(categoryId).isEmpty())
            return false;
        else
            return true;
    }

    public boolean hasCategoryById(int categoryId) {
        return categoryRepository.existsById(categoryId);
    }

    @PostConstruct
    public void initLinkNames() {
        List<Category> categoriesWithoutLinkName = categoryRepository.findByLinkNameIsNull();
        for (Category category : categoriesWithoutLinkName) {
            category.generateProductData();
            System.out.println("linkName: "+category.getLinkName());
            categoryRepository.save(category);
        }
    }

    public boolean existById(int categoryId) {
        return categoryRepository.existsById(categoryId);
    }

    public boolean existByLinkName(String linkName) {
        return categoryRepository.existsByLinkName(linkName);
    }
}
