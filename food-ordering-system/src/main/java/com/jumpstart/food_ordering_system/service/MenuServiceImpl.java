package com.jumpstart.food_ordering_system.service;

import com.jumpstart.food_ordering_system.dto.MenuDto;
import com.jumpstart.food_ordering_system.entity.Category;
import com.jumpstart.food_ordering_system.entity.Menu;
import com.jumpstart.food_ordering_system.repository.MenuRepository;
import com.jumpstart.food_ordering_system.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public Response<MenuDto> addMenu(MenuDto menuDto) {
        Menu menu = mapToEntity(menuDto);
        Menu savedMenu = menuRepository.save(menu);
        return Response.success(mapToDto(savedMenu), "Menu item created successfully");
    }

    @Override
    public Response<List<MenuDto>> getAllMenus() {
        List<MenuDto> list = menuRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return Response.success(list, "All menu items retrieved");
    }

    @Override
    public Response<MenuDto> getMenuById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found with id: " + id));
        return Response.success(mapToDto(menu), "Menu item retrieved successfully");
    }

    // Private Mapper: Convert DTO to Entity
    private Menu mapToEntity(MenuDto dto) {
        Menu menu = new Menu();
        menu.setId(dto.getId());
        menu.setName(dto.getName());
        menu.setDescription(dto.getDescription());
        menu.setPrice(dto.getPrice());
        menu.setImageUrl(dto.getImageUrl());

        Category category = new Category();
        category.setId(dto.getCategoryId());
        menu.setCategory(category);

        return menu;
    }

    // Private Mapper: Convert Entity to DTO
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