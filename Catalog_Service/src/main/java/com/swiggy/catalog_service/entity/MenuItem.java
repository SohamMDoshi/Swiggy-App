package com.swiggy.catalog_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swiggy.catalog_service.requestModels.UpdateMenuItemRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.ConstraintMode.CONSTRAINT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "restaurant_id", foreignKey = @ForeignKey(value = CONSTRAINT,
            foreignKeyDefinition = "FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE CASCADE"))
    private Restaurant restaurant;

    public void update(UpdateMenuItemRequest request) {
    }
}
