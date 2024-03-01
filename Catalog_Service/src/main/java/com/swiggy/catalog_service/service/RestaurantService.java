package com.swiggy.catalog_service.service;

import com.swiggy.catalog_service.entity.Restaurant;
import com.swiggy.catalog_service.requestModels.CreateRestaurantRequest;
import com.swiggy.catalog_service.requestModels.UpdateRestaurantRequest;


import java.util.List;

public interface RestaurantService {
    List<Restaurant> getAllRestaurants();
    Restaurant createRestaurant(CreateRestaurantRequest request);
    Restaurant updateRestaurant(Long restaurantId, UpdateRestaurantRequest request);
    Restaurant getRestaurant(Long restaurantId);
    String deleteRestaurant(Long restaurantId);
}
