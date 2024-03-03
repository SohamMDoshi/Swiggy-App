package com.swiggy.order_service.requestModel;

import com.swiggy.order_service.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Long restaurantId;
    private List<OrderItem> orderItems;
}
