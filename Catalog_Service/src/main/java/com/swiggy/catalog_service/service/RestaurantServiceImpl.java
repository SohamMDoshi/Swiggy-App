package com.swiggy.catalog_service.service;

import com.swiggy.catalog_service.entity.Restaurant;
import com.swiggy.catalog_service.exception.RestaurantNotFoundException;
import com.swiggy.catalog_service.repository.RestaurantRepository;
import com.swiggy.catalog_service.requestModels.CreateRestaurantRequest;
import com.swiggy.catalog_service.requestModels.UpdateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest request) {
        Restaurant restaurant = new Restaurant(request);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, UpdateRestaurantRequest request) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
        restaurant.update(request);
        return null;
    }

    @Override
    public Restaurant getRestaurant(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
    }

    @Override
    public String deleteRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
        restaurantRepository.delete(restaurant);
        return "Restaurant got deleted successfully with id " + restaurantId;
    }
}
