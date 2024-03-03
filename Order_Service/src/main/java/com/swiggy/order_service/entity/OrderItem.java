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
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long menuItemId;
    private Long quantity;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "orders_id", foreignKey = @ForeignKey(value = CONSTRAINT,
            foreignKeyDefinition = "FOREIGN KEY (orders_id) REFERENCES orders(id) ON DELETE CASCADE"))
    private Order order;
}

/*
* Oder{
*   [
*       orderItem : {
*           menuItemId :
*           quantity :
*       },
*       orderItem : {
 *       menuItemId :
 *       quantity :
 *      }
*   ]
* }
* */
