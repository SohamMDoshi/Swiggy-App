package com.swiggy.order_service.responseModel;

import com.swiggy.order_service.dto.AssignedOrderDetails;
import com.swiggy.order_service.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Order order;
    private MenuItemResponse menuItems;
    private AssignedOrderDetails assignedOrderDetails;
}
