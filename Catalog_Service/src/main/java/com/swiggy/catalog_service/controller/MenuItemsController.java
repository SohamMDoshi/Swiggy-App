package com.swiggy.catalog_service.controller;

import com.swiggy.catalog_service.entity.MenuItem;
import com.swiggy.catalog_service.requestModels.UpdateMenuItemRequest;
import com.swiggy.catalog_service.response.MenuItemResponse;
import com.swiggy.catalog_service.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/catalog-service/restaurants/{restaurantId}/menu-items")
public class MenuItemsController {

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<MenuItem> create(@PathVariable Long restaurantId, @RequestBody MenuItem menuItem) {
        MenuItem menuItem1 = menuItemService.addNew(restaurantId,menuItem);
        return new ResponseEntity<>(menuItem1, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MenuItem>> getAll(@PathVariable Long restaurantId) {
        List<MenuItem> menuItems1 = menuItemService.getAll(restaurantId);
        return new ResponseEntity<>(menuItems1, HttpStatus.OK);
    }


    @GetMapping("/{menuItemId}")
    public ResponseEntity<MenuItem> get(@PathVariable Long restaurantId,@PathVariable Long menuItemId) {
        MenuItem menuItem = menuItemService.get(restaurantId,menuItemId);
        return new ResponseEntity<>(menuItem, HttpStatus.OK);
    }

    @GetMapping("/specific-list")
    public ResponseEntity<MenuItemResponse> getMenuList(@PathVariable Long restaurantId, @RequestParam List<Long> menuItemsIds) {
        MenuItemResponse response = menuItemService.getMenuItemsByRestaurant(restaurantId,menuItemsIds);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{menuItemId}")
    public ResponseEntity<MenuItem> update(@PathVariable Long menuItemId, @RequestBody UpdateMenuItemRequest request) {
        MenuItem menuItem = menuItemService.updateDetails(menuItemId,request);
        return new ResponseEntity<>(menuItem, HttpStatus.OK);
    }

    @DeleteMapping("/{menuItemId}")
    public ResponseEntity<String> delete(@PathVariable Long menuItemId) {
        String msg = menuItemService.delete(menuItemId);
        return new ResponseEntity<>(msg,HttpStatus.ACCEPTED);
    }
}
