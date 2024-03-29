package com.swiggy.catalog_service.response;

import com.swiggy.catalog_service.entity.MenuItem;
import com.swiggy.catalog_service.entity.Restaurant;
import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemResponse {
    private Restaurant restaurant;
    private List<MenuItem> menuItems;
}
