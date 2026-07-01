package com.jumpstart.food_ordering_system.controller;

import com.jumpstart.food_ordering_system.dto.MenuDto;
import com.jumpstart.food_ordering_system.response.Response;
import com.jumpstart.food_ordering_system.service.MenuService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min; // <-- 1. Add this import
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated; // <-- 2. Add this import
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor

public class MenuController {

    private final MenuService menuService;

    // 1. CREATE
    @PostMapping
    public ResponseEntity<Response<MenuDto>> create(@RequestBody @Valid MenuDto dto) {
        return ResponseEntity.ok(menuService.createMenu(dto));
    }

    // 2. READ ALL (with Filters, Pagination, and Sorting)
    @GetMapping
    public ResponseEntity<Response<Map<String, Object>>> all(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String sort) {

        return ResponseEntity.ok(menuService.getAllMenus(categoryId, search, page, size, sort));
    }

    // 3. READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Response<MenuDto>> byId(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getMenuById(id));
    }

    // 4. UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Response<MenuDto>> update(
            @PathVariable Long id,
            @RequestBody @Valid MenuDto dto) {
        return ResponseEntity.ok(menuService.updateMenu(id, dto));
    }

    // 5. DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.deleteMenu(id));
    }
}