package com.jumpstart.food_ordering_system.dto;

// This is a Data Transfer Object (DTO).
public class CategoryDto {
    private Long id;
    private String name;

    // --- MANUAL GETTERS & SETTERS TO BYPASS LOMBOK ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}