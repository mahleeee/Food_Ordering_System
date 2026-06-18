package com.jumpstart.food_ordering_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// This class is a JPA Entity. It maps directly to the 'category' table in our MySQL database.
@Entity
@Table(name = "category")
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    // --- MANUALLY ADDED GETTERS AND SETTERS ---

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
