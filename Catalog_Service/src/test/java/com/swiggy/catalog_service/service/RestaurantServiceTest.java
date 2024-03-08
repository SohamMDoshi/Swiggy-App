package com.swiggy.catalog_service.service;

import com.swiggy.catalog_service.entity.Restaurant;
import com.swiggy.catalog_service.repository.RestaurantRepository;
import com.swiggy.catalog_service.requestModels.RestaurantRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @Test
    public void testCreate() {
        RestaurantRequest request = mock(RestaurantRequest.class);
        Restaurant restaurant = mock(Restaurant.class);

        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        Restaurant result = restaurantService.create(request);

        assertEquals(result,restaurant);
        verify(restaurantRepository,times(1)).save(any(Restaurant.class));
    }

    @Test
    public void testGet() {
        Restaurant restaurant = mock(Restaurant.class);
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));

        Restaurant result = restaurantService.get(1L);

        assertEquals(result,restaurant);
        verify(restaurantRepository,times(1)).findById(anyLong());
    }

    @Test
    public void testGetAll() {
        List<Restaurant> restaurants = Arrays.asList(mock(Restaurant.class),mock(Restaurant.class));
        when(restaurantRepository.findAll()).thenReturn(restaurants);

        List<Restaurant> result = restaurantService.getAll();

        assertEquals(restaurants.toString(),result.toString());
        verify(restaurantRepository,times(1)).findAll();
    }

    @Test
    public void testUpdate() {
        RestaurantRequest request = mock(RestaurantRequest.class);
        Restaurant restaurant = mock(Restaurant.class);

        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        Restaurant result = restaurantService.update(1L,request);

        assertEquals(restaurant,result);
        verify(restaurantRepository,times(1)).findById(anyLong());
        verify(restaurantRepository,times(1)).save(any(Restaurant.class));
    }
}
