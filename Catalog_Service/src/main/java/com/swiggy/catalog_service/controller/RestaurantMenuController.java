package com.swiggy.catalog_service.controller;

import com.swiggy.catalog_service.entity.MenuItem;
import com.swiggy.catalog_service.response.MenuItemResponse;
import com.swiggy.catalog_service.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/catalog-service/restaurants")
public class RestaurantMenuController {

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping("/menu-items")
    public ResponseEntity<List<MenuItemResponse>> getMenuItemsByRestaurant(@RequestBody Map<Long, List<Long>> restaurantMenuRequest) {
        List<MenuItemResponse> menuItems = menuItemService.getMenuItemsByRestaurant(restaurantMenuRequest);
        return new ResponseEntity<>(menuItems, HttpStatus.OK);
    }
}