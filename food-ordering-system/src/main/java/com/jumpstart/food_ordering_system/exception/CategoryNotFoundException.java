package com.jumpstart.food_ordering_system.exception;

//  This is a custom exception class. 
// Its purpose is to allow us to throw a meaningful, specific error message 
// when a requested category cannot be found in the database, making debugging and API responses cleaner.
public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}