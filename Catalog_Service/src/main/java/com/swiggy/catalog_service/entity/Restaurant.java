package com.swiggy.catalog_service.entity;

import com.swiggy.catalog_service.requestModels.CreateRestaurantRequest;
import com.swiggy.catalog_service.requestModels.UpdateRestaurantRequest;
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
    @OneToOne
    private Location location;

    public Restaurant(CreateRestaurantRequest request) {
    }

    public void update(UpdateRestaurantRequest request) {
    }
}
