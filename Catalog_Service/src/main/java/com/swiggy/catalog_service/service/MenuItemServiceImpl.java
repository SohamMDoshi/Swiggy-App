package com.swiggy.catalog_service.service;

import com.swiggy.catalog_service.entity.MenuItem;
import com.swiggy.catalog_service.exception.MenuItemNotFoundException;
import com.swiggy.catalog_service.exception.RestaurantNotFoundException;
import com.swiggy.catalog_service.repository.MenuItemRepository;
import com.swiggy.catalog_service.repository.RestaurantRepository;
import com.swiggy.catalog_service.requestModels.UpdateMenuItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService{

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<MenuItem> getList(Long restaurantId) {
        return menuItemRepository.findByRestaurntId(restaurantId);
    }

    @Override
    public MenuItem addNew(Long restaurantId, MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem getDetails(Long restaurantId, Long menuItemId) {
        restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
        return menuItemRepository.findByRestaurntIdAndMenuItemId(restaurantId,menuItemId);
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
