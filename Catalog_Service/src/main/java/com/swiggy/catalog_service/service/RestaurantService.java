package com.swiggy.catalog_service.service;

import com.swiggy.catalog_service.entity.Restaurant;
import com.swiggy.catalog_service.requestModels.RestaurantRequest;


import java.util.List;

public interface RestaurantService {
    List<Restaurant> getAll();
    Restaurant create(RestaurantRequest request);
    Restaurant update(Long restaurantId, RestaurantRequest request);
    Restaurant get(Long restaurantId);
    String delete(Long restaurantId);
}
