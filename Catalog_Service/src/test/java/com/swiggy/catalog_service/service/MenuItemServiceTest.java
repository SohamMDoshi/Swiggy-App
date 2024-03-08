package com.swiggy.catalog_service.service;

import com.swiggy.catalog_service.entity.MenuItem;
import com.swiggy.catalog_service.entity.Restaurant;
import com.swiggy.catalog_service.repository.MenuItemRepository;
import com.swiggy.catalog_service.repository.RestaurantRepository;
import com.swiggy.catalog_service.requestModels.UpdateMenuItemRequest;
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
public class MenuItemServiceTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private MenuItemServiceImpl menuItemService;

    @Test
    void testAddNew() {
        MenuItem menuItem = mock(MenuItem.class);
        Restaurant restaurant = mock(Restaurant.class);
        menuItem.setRestaurant(restaurant);

        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));

        MenuItem result = menuItemService.addNew(1L,menuItem);

        assertEquals(menuItem,result);
        verify(menuItemRepository,times(1)).save(any(MenuItem.class));
        verify(restaurantRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGet() {
        MenuItem menuItem = mock(MenuItem.class);
        Restaurant restaurant = mock(Restaurant.class);
        menuItem.setRestaurant(restaurant);

        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(menuItemRepository.findByRestaurantIdAndMenuItemId(anyLong(),anyLong())).thenReturn(menuItem);

        MenuItem result = menuItemService.get(1L,1L);

        assertEquals(menuItem, result);
        verify(restaurantRepository, times(1)).findById(anyLong());
        verify(menuItemRepository, times(1)).findByRestaurantIdAndMenuItemId(anyLong(),anyLong());
    }

    @Test
    void testGetAll() {
        List<MenuItem> menuItems = Arrays.asList(mock(MenuItem.class),mock(MenuItem.class));
        Restaurant restaurant = mock(Restaurant.class);

        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(menuItemRepository.findByRestaurantId(anyLong())).thenReturn(menuItems);

        List<MenuItem> result = menuItemService.getAll(1L);

        assertEquals(menuItems.toString(), result.toString());
        verify(restaurantRepository, times(1)).findById(anyLong());
        verify(menuItemRepository, times(1)).findByRestaurantId(anyLong());
    }

    @Test
    void testUpdateDetails() {
        MenuItem menuItem = mock(MenuItem.class);
        UpdateMenuItemRequest request = mock(UpdateMenuItemRequest.class);

        when(menuItemRepository.findById(anyLong())).thenReturn(Optional.of(menuItem));
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);

        MenuItem result = menuItemService.updateDetails(1L,request);

        assertEquals(menuItem,result);
        verify(menuItemRepository, times(1)).findById(anyLong());
        verify(menuItemRepository, times(1)).save(any(MenuItem.class));
    }
}
