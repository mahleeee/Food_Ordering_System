package com.jumpstart.food_ordering_system.repository;

import com.jumpstart.food_ordering_system.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//  This is the Repository layer. 
// Its responsibility is to handle direct communication with the database. 
// By extending JpaRepository, Spring automatically gives us built-in CRUD methods (like findAll, save, delete) without us writing any SQL queries.
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}