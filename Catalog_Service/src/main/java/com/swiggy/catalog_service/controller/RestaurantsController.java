package com.swiggy.catalog_service.controller;

import com.swiggy.catalog_service.entity.Restaurant;
import com.swiggy.catalog_service.requestModels.RestaurantRequest;
import com.swiggy.catalog_service.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants")
public class RestaurantsController {

    @Autowired
    private RestaurantService restaurantService;


    @PostMapping
    public ResponseEntity<Restaurant> create(@RequestBody RestaurantRequest request) {
        Restaurant restaurant = restaurantService.create(request);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> get(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.get(restaurantId);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> update(@PathVariable Long restaurantId, @RequestBody RestaurantRequest request) {
        Restaurant restaurant = restaurantService.update(restaurantId,request);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<String> delete(@PathVariable Long restaurantId) {
        String message = restaurantService.delete(restaurantId);
        return new ResponseEntity<>(message,HttpStatus.ACCEPTED);
    }
}
