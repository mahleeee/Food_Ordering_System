package com.jumpstart.food_ordering_system.controller;

import com.jumpstart.food_ordering_system.dto.CategoryDto;
import com.jumpstart.food_ordering_system.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//This is the Controller layer (REST Controller).
// It acts as the entry point for incoming HTTP requests from clients (like a web browser or Postman). 
// Its responsibility is to listen to a specific URL path, handle the request, call the Service layer, and return the data.
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    // Injecting the CategoryService to access our business logic
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // This method handles GET requests sent to http://localhost:8085/api/category
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}