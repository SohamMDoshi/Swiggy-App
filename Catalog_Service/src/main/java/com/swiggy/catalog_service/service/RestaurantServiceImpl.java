package com.swiggy.catalog_service.service;

import com.swiggy.catalog_service.entity.Restaurant;
import com.swiggy.catalog_service.exception.RestaurantNotFoundException;
import com.swiggy.catalog_service.repository.RestaurantRepository;
import com.swiggy.catalog_service.requestModels.RestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant create(RestaurantRequest request) {
        Restaurant restaurant = new Restaurant(request);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant update(Long restaurantId, RestaurantRequest request) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
        restaurant.update(request);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant get(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
    }

    @Override
    public String delete(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
        restaurantRepository.delete(restaurant);
        return "Restaurant got deleted successfully with id " + restaurantId;
    }
}
