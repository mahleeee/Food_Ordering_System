package com.jumpstart.food_ordering_system.service;

import com.jumpstart.food_ordering_system.dto.MenuDto;
import com.jumpstart.food_ordering_system.response.Response;
import java.util.List;

public interface MenuService {
    Response<MenuDto> addMenu(MenuDto menuDto);
    Response<List<MenuDto>> getAllMenus();
    Response<MenuDto> getMenuById(Long id);
}