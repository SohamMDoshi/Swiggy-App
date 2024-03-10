package com.swiggy.catalog_service.service;

import com.swiggy.catalog_service.entity.MenuItem;
import com.swiggy.catalog_service.entity.Restaurant;
import com.swiggy.catalog_service.exception.MenuItemNotFoundException;
import com.swiggy.catalog_service.exception.RestaurantNotFoundException;
import com.swiggy.catalog_service.repository.MenuItemRepository;
import com.swiggy.catalog_service.repository.RestaurantRepository;
import com.swiggy.catalog_service.requestModels.UpdateMenuItemRequest;
import com.swiggy.catalog_service.response.MenuItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuItemServiceImpl implements MenuItemService{

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<MenuItem> getAll(Long restaurantId) {
        restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public List<MenuItemResponse> getMenuItemsByRestaurant(Map<Long, List<Long>> restaurantMenuItemIdsMap) {
        List<MenuItemResponse> response = new ArrayList<>();
        restaurantMenuItemIdsMap.forEach((restaurantId, menuItemIds) -> {
            Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
            List<MenuItem> menuItems = menuItemRepository.findByRestaurant_IdAndIdIn(restaurantId,menuItemIds);
            response.add(new MenuItemResponse(restaurant, menuItems));
        });
        return response;
    }

    @Override
    public MenuItemResponse getMenuItemsByRestaurant(Long restaurantId, List<Long> menuItemIds) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
        List<MenuItem> menuItems = menuItemRepository.findByRestaurant_IdAndIdIn(restaurantId,menuItemIds);
         return new MenuItemResponse(restaurant,menuItems);
    }

    @Override
    public MenuItem addNew(Long restaurantId, MenuItem menuItem) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
        menuItem.setRestaurant(restaurant);
        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem get(Long restaurantId, Long menuItemId) {
        restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
        return menuItemRepository.findByRestaurantIdAndMenuItemId(restaurantId,menuItemId);
    }

    @Override
    public MenuItem updateDetails(Long menuItemId, UpdateMenuItemRequest request) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(()-> new MenuItemNotFoundException(menuItemId));
        menuItem.update(request);
        return menuItemRepository.save(menuItem);
    }

    @Override
    public String delete(Long menuItemId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(()-> new MenuItemNotFoundException(menuItemId));
        menuItemRepository.delete(menuItem);
        return "Menu Item got deleted successfully with id "+menuItemId;
    }

}
