package com.jumpstart.food_ordering_system.service;

import com.jumpstart.food_ordering_system.dto.CategoryDto;
import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();     // GET all
    CategoryDto getCategoryById(Long id);     // GET by id [Task 4.1]
    CategoryDto addCategory(CategoryDto dto); // POST create [Task 4.2]
    CategoryDto updateCategory(Long id, CategoryDto dto); // PUT update [Task 4.4]
    void deleteCategory(Long id);             // DELETE [Task 4.5]
}