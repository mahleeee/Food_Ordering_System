package com.jumpstart.food_ordering_system.service;

import com.jumpstart.food_ordering_system.dto.MenuDto;
import com.jumpstart.food_ordering_system.response.Response;
import java.util.Map;

public interface MenuService {
    Response<MenuDto> createMenu(MenuDto dto); // Renamed from addMenu to match standard spec
    Response<Map<String, Object>> getAllMenus(Long categoryId, String search, int page, int size, String sort); // Type fixed to Map<String, Object>
    Response<MenuDto> getMenuById(Long id);
    Response<MenuDto> updateMenu(Long id, MenuDto dto);
    Response<Void> deleteMenu(Long id);
}