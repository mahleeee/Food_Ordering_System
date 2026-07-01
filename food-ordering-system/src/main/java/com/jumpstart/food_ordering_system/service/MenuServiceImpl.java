package com.jumpstart.food_ordering_system.service;

import com.jumpstart.food_ordering_system.dto.MenuDto;
import com.jumpstart.food_ordering_system.entity.Category;
import com.jumpstart.food_ordering_system.entity.Menu;
import com.jumpstart.food_ordering_system.repository.CategoryRepository;
import com.jumpstart.food_ordering_system.repository.MenuRepository;
import com.jumpstart.food_ordering_system.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Response<MenuDto> createMenu(MenuDto menuDto) {
        Category category = categoryRepository.findById(menuDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + menuDto.getCategoryId()));

        Menu menu = mapToEntity(menuDto);
        menu.setCategory(category);

        Menu savedMenu = menuRepository.save(menu);
        return Response.success(mapToDto(savedMenu), "Menu item created successfully");
    }

    @Override
    public Response<Map<String, Object>> getAllMenus(Long categoryId, String search, int page, int size, String sort) {
        Sort sortOrder = Sort.by("id").ascending();
        if (sort != null && sort.contains(",")) {
            String[] sortParts = sort.split(",");
            String sortField = sortParts[0];
            String sortDirection = sortParts[1];
            sortOrder = sortDirection.equalsIgnoreCase("desc") ?
                    Sort.by(sortField).descending() : Sort.by(sortField).ascending();
        }

        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Specification<Menu> spec = Specification.where(null);

        if (categoryId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("category").get("id"), categoryId));
        }

        if (search != null && !search.trim().isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + search.toLowerCase() + "%"));
        }

        Page<Menu> menuPage = menuRepository.findAll(spec, pageable);

        List<MenuDto> content = menuPage.getContent().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("menus", content);
        responseData.put("totalElements", menuPage.getTotalElements());
        responseData.put("totalPages", menuPage.getTotalPages());
        responseData.put("number", menuPage.getNumber());
        responseData.put("size", menuPage.getSize());
        responseData.put("first", menuPage.isFirst());
        responseData.put("last", menuPage.isLast());

        return Response.success(responseData, "Menu items retrieved successfully");
    }

    @Override
    public Response<MenuDto> getMenuById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found with id: " + id));
        return Response.success(mapToDto(menu), "Menu item retrieved successfully");
    }

    @Override
    public Response<MenuDto> updateMenu(Long id, MenuDto menuDto) {
        Menu existingMenu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found with id: " + id));

        Category category = categoryRepository.findById(menuDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + menuDto.getCategoryId()));

        existingMenu.setName(menuDto.getName());
        existingMenu.setDescription(menuDto.getDescription());
        existingMenu.setPrice(menuDto.getPrice());
        existingMenu.setImageUrl(menuDto.getImageUrl());
        existingMenu.setCategory(category);

        Menu updatedMenu = menuRepository.save(existingMenu);
        return Response.success(mapToDto(updatedMenu), "Menu item updated successfully");
    }

    @Override
    public Response<Void> deleteMenu(Long id) {
        if (!menuRepository.existsById(id)) {
            throw new RuntimeException("Menu item not found with id: " + id);
        }
        menuRepository.deleteById(id);
        return Response.success(null, "Menu item deleted successfully");
    }

    private Menu mapToEntity(MenuDto dto) {
        Menu menu = new Menu();
        menu.setId(dto.getId());
        menu.setName(dto.getName());
        menu.setDescription(dto.getDescription());
        menu.setPrice(dto.getPrice());
        menu.setImageUrl(dto.getImageUrl());
        return menu;
    }

    private MenuDto mapToDto(Menu menu) {
        return MenuDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .imageUrl(menu.getImageUrl())
                .categoryId(menu.getCategory() != null ? menu.getCategory().getId() : null)
                .categoryName(menu.getCategory() != null ? menu.getCategory().getName() : null)
                .build();
    }
}