package com.swiggy.catalog_service.repository;

import com.swiggy.catalog_service.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {
    List<MenuItem> findByRestaurntId(Long restaurantId);

    MenuItem findByRestaurntIdAndMenuItemId(Long restaurantId, Long menuItemId);
}
