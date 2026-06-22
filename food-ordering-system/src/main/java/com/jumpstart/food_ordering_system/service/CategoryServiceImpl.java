package com.jumpstart.food_ordering_system.service;

import com.jumpstart.food_ordering_system.dto.CategoryDto;
import com.jumpstart.food_ordering_system.entity.Category;
import com.jumpstart.food_ordering_system.exception.CategoryNotFoundException;
import com.jumpstart.food_ordering_system.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> {
            CategoryDto dto = new CategoryDto();
            dto.setId(category.getId());
            dto.setName(category.getName());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));

        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    @Override
    public CategoryDto addCategory(CategoryDto dto) {
        Category category = new Category();
        category.setName(dto.getName());

        Category savedCategory = categoryRepository.save(category);

        CategoryDto responseDto = new CategoryDto();
        responseDto.setId(savedCategory.getId());
        responseDto.setName(savedCategory.getName());
        return responseDto;
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));

        category.setName(dto.getName());
        Category updatedCategory = categoryRepository.save(category);

        CategoryDto responseDto = new CategoryDto();
        responseDto.setId(updatedCategory.getId());
        responseDto.setName(updatedCategory.getName());
        return responseDto;
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));

        categoryRepository.delete(category);
    }
}