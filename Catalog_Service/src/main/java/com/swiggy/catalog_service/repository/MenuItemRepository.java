package com.swiggy.catalog_service.repository;

import com.swiggy.catalog_service.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {
    @Query(value = "SELECT * FROM public.menu_item WHERE restaurant_id =?1", nativeQuery = true)
    List<MenuItem> findByRestaurantId(Long restaurantId);

    @Query(value = "SELECT * FROM public.menu_item WHERE restaurant_id = ?1 AND id =?2", nativeQuery = true)
    MenuItem findByRestaurantIdAndMenuItemId(Long restaurantId, Long menuItemId);

    List<MenuItem> findByRestaurant_IdAndIdIn(Long restaurantId,List<Long> ids);
}
