package com.swiggy.catalog_service.entity;

import com.swiggy.catalog_service.requestModels.RestaurantRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Location location;

    public Restaurant(RestaurantRequest request) {
        this.name = request.getName();
        this.location = request.getLocation();
    }

    public void update(RestaurantRequest request) {
        this.name = request.getName();
        this.location = request.getLocation();
    }
}
