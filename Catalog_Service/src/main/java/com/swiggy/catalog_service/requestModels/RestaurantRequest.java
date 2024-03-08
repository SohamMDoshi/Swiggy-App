package com.swiggy.catalog_service.requestModels;

import com.swiggy.catalog_service.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRequest {
    private String name;
    private Location location;
}
