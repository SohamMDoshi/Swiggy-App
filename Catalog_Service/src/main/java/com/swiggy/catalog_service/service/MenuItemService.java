package com.swiggy.catalog_service.service;

import com.swiggy.catalog_service.entity.MenuItem;
import com.swiggy.catalog_service.requestModels.UpdateMenuItemRequest;
import com.swiggy.catalog_service.response.MenuItemResponse;

import java.util.List;
import java.util.Map;

public interface MenuItemService {

    List<MenuItem> getAll(Long restaurantId);
    List<MenuItemResponse> getMenuItemsByRestaurant(Map<Long, List<Long>> restaurantMenuItemIdsMap);

    MenuItemResponse getMenuItemsByRestaurant(Long restaurantId, List<Long> menuItemIds);
    MenuItem addNew(Long restaurantId, MenuItem menuItem);

    MenuItem get(Long restaurantId, Long menuItemId);

    MenuItem updateDetails(Long menuItemId, UpdateMenuItemRequest request);

    String delete(Long menuItemId);

}
