package com.swiggy.order_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.ConstraintMode.CONSTRAINT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "users_id", foreignKey = @ForeignKey(value = CONSTRAINT,
            foreignKeyDefinition = "FOREIGN KEY (users_id) REFERENCES users(id) ON DELETE CASCADE"))
    private User user;
    private Double totalPrice;

    public Order(User user,Double totalPrice) {
        this.user = user;
        this.totalPrice = totalPrice;
    }

    public void assignDeliveryPersonnel(Order order) {

    }
}
