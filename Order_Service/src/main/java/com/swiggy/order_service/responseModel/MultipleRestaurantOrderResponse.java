package com.swiggy.order_service.responseModel;

import com.swiggy.order_service.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultipleRestaurantOrderResponse {
    private Order order;
    private List<MenuItemResponse> menuItems;
}
