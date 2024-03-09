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
    private Restaurant restaurantId;
    private List<MenuItem> menuItems;

    public static void setQuantity(List<OrderRequest> requests, List<MenuItemResponse> menuItemsResponse) {
        for (OrderRequest request : requests) {
            Long restaurantId = request.getRestaurantId();
            List<OrderItem> orderItems = request.getOrderItems();

            // Find the corresponding MenuItemResponse in the menuItemsResponse list
            Optional<MenuItemResponse> menuItemResponseOptional = menuItemsResponse.stream()
                    .filter(menuItemResponse -> menuItemResponse.getRestaurantId().equals(restaurantId))
                    .findFirst();

            // If MenuItemResponse found, update its menuItems with quantity information
            menuItemResponseOptional.ifPresent(menuItemResponse -> {
                List<MenuItem> menuItems = menuItemResponse.getMenuItems();
                for (MenuItem menuItem : menuItems) {
                    Long menuItemId = menuItem.getId();
                    // Find the matching OrderItem
                    Optional<OrderItem> matchingOrderItem = orderItems.stream()
                            .filter(orderItem -> orderItem.getMenuItemId().equals(menuItemId))
                            .findFirst();
                    // If matching OrderItem found, set the quantity
                    matchingOrderItem.ifPresent(orderItem -> menuItem.setQuantity(orderItem.getQuantity()));
                }
            });
        }
    }

    public Double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (MenuItem menuItem : menuItems) {
            // Calculate total price for each MenuItem
            double price = menuItem.getPrice() * menuItem.getQuantity();
            totalPrice+=price;
        }
        return totalPrice;
    }
}
