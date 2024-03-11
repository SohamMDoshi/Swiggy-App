package com.swiggy.catalog_service.controller;

import com.swiggy.catalog_service.entity.Restaurant;
import com.swiggy.catalog_service.requestModels.RestaurantRequest;
import com.swiggy.catalog_service.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
@RestController
@RequestMapping("/catalog-service/restaurants")
public class RestaurantsController {

    @Autowired
    private RestaurantService restaurantService;


    @PostMapping
    public ResponseEntity<Restaurant> create(@Valid @RequestBody RestaurantRequest request) {
        Restaurant restaurant = restaurantService.create(request);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAll(){
        List<Restaurant> restaurants = restaurantService.getAll();
        return new ResponseEntity<>(restaurants,HttpStatus.OK);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> get(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.get(restaurantId);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> update(@PathVariable Long restaurantId, @Valid@RequestBody RestaurantRequest request) {
        Restaurant restaurant = restaurantService.update(restaurantId,request);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<String> delete(@PathVariable Long restaurantId) {
        String message = restaurantService.delete(restaurantId);
        return new ResponseEntity<>(message,HttpStatus.ACCEPTED);
    }
}
