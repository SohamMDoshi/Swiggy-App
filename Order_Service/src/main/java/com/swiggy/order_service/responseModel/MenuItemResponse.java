package com.swiggy.order_service.responseModel;

import com.swiggy.order_service.dto.MenuItem;
import com.swiggy.order_service.dto.Restaurant;
import com.swiggy.order_service.entity.OrderItem;
import com.swiggy.order_service.requestModel.OrderRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemResponse {
    private Restaurant restaurant;
    private List<MenuItem> menuItems;

    public void setQuantity(OrderRequest request) {
        Long restaurantId = request.getRestaurantId();
        List<OrderItem> orderItems = request.getOrderItems();

        if (this.restaurant != null && this.restaurant.getId().equals(restaurantId)) {
            List<MenuItem> menuItems = this.menuItems;
            for (MenuItem menuItem : menuItems) {
                Long menuItemId = menuItem.getId();
                Optional<OrderItem> matchingOrderItem = orderItems.stream()
                        .filter(orderItem -> orderItem.getMenuItemId().equals(menuItemId))
                        .findFirst();

                matchingOrderItem.ifPresent(orderItem -> menuItem.setQuantity(orderItem.getQuantity()));
            }
        }
    }

    public Double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (MenuItem menuItem : menuItems) {
            double price = menuItem.getPrice() * menuItem.getQuantity();
            totalPrice+=price;
        }
        return totalPrice;
    }
}
