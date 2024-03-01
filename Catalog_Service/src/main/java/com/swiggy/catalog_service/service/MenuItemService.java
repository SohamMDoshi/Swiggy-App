package com.swiggy.catalog_service.service;

import com.swiggy.catalog_service.entity.MenuItem;
import com.swiggy.catalog_service.requestModels.UpdateMenuItemRequest;

import java.util.List;

public interface MenuItemService {

    List<MenuItem> getAll(Long restaurantId);
    MenuItem addNew(Long restaurantId, MenuItem menuItem);

    MenuItem get(Long restaurantId, Long menuItemId);

    MenuItem updateDetails(Long menuItemId, UpdateMenuItemRequest request);

    String delete(Long menuItemId);

}




/*
*
     - GET : Get the menu items of a specific restaurant.  - /restaurants/{restaurantID}/menu
     - POST : Add a new menu item to the restaurant's menu (accessible to super admin).
     - GET : Get details of a specific menu item. /restaurants/{restaurantID}/menu/{menuItemID}
     - PUT : Update details of a menu item (accessible to super admin).
     - DELETE : Delete a menu item (accessible to super admin).

* */