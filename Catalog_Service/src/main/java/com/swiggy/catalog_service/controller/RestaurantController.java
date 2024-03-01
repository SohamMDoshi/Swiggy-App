package com.swiggy.catalog_service.controller;

import com.swiggy.catalog_service.entity.Restaurant;
import com.swiggy.catalog_service.requestModels.CreateRestaurantRequest;
import com.swiggy.catalog_service.requestModels.UpdateRestaurantRequest;
import com.swiggy.catalog_service.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;


    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest request) {
        Restaurant restaurant = restaurantService.createRestaurant(request);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurant(restaurantId);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long restaurantId, @RequestBody UpdateRestaurantRequest request) {
        Restaurant restaurant = restaurantService.updateRestaurant(restaurantId,request);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long restaurantId) {
        String message = restaurantService.deleteRestaurant(restaurantId);
        return new ResponseEntity<>(message,HttpStatus.ACCEPTED);
    }
}
