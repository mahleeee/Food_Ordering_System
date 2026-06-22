package com.jumpstart.food_ordering_system.service;

import com.jumpstart.food_ordering_system.dto.CategoryDto;
import java.util.List;

public interface CategoryService {
    //1.Get all categories
    List<CategoryDto> getAllCategories();

    //2.Get a single category by its ID
    CategoryDto getCategoryById(Long id);

    //3.Add a brand new category
    CategoryDto addCategory(CategoryDto dto);

    //4.Update an existing category
    CategoryDto updateCategory(Long id, CategoryDto dto);

    //5.Delete a category by its ID
    void deleteCategory(Long id);
}