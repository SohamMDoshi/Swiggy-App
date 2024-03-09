package com.swiggy.order_service.service;

import com.swiggy.order_service.client.MenuItemsClient;
import com.swiggy.order_service.entity.Order;
import com.swiggy.order_service.entity.OrderItem;
import com.swiggy.order_service.entity.User;
import com.swiggy.order_service.exception.OrderNotFoundException;
import com.swiggy.order_service.repository.OrderRepository;
import com.swiggy.order_service.requestModel.OrderRequest;
import com.swiggy.order_service.responseModel.MenuItemResponse;
import com.swiggy.order_service.responseModel.MultipleRestaurantOrderResponse;
import com.swiggy.order_service.responseModel.SingleRestaurantOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.swiggy.order_service.responseModel.MenuItemResponse.setQuantity;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemService orderItemService;

    private final MenuItemsClient menuItemsClient;

    public OrderServiceImpl(MenuItemsClient menuItemsClient) {
        this.menuItemsClient = menuItemsClient;
    }


    @Override
    public SingleRestaurantOrderResponse create(OrderRequest request, User user) {
        List<Long> menuItemIds = request.getOrderItems().stream()
                .map(OrderItem::getMenuItemId).toList();
        MenuItemResponse response = menuItemsClient.getMenuList(request.getRestaurantId(),menuItemIds);
        Double totalPrice = response.calculateTotalPrice();

        Order order = new Order(user,totalPrice);
        Order savedOrder = orderRepository.save(order);
        orderItemService.create(request.getOrderItems(),savedOrder);
        return new SingleRestaurantOrderResponse(order,response);
    }


    @Override
    public MultipleRestaurantOrderResponse create(List<OrderRequest> requests, User user) {
        Map<Long, List<Long>> restaurantIdToMenuIdsMap = requests.stream()
                .collect(Collectors.toMap(
                        OrderRequest::getRestaurantId,
                        orderRequest -> orderRequest.getOrderItems().stream()
                                .map(OrderItem::getMenuItemId)
                                .collect(Collectors.toList())
                ));

        List<MenuItemResponse> menuItemsResponse  = menuItemsClient.getListOfRestaurantsAndMenuItems(restaurantIdToMenuIdsMap);

        setQuantity(requests, menuItemsResponse);
        Double totalPrice = getTotalPrice(menuItemsResponse);

        Order order = new Order(user,totalPrice);
        Order savedOrder = orderRepository.save(order);

        for(OrderRequest request : requests) orderItemService.create(request.getOrderItems(),savedOrder);
        return new MultipleRestaurantOrderResponse(order,menuItemsResponse);
    }

    @Override
    public Order get(Long id) {
        return orderRepository.findById(id).orElseThrow(()-> new OrderNotFoundException(id));
    }

    private static Double getTotalPrice(List<MenuItemResponse> menuItemsResponse) {
        Double totalPrice = 0.0;
        for(MenuItemResponse res : menuItemsResponse) {
            totalPrice+= res.calculateTotalPrice();
        }
        return totalPrice;
    }
}
