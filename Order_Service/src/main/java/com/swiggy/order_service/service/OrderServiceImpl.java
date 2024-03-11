package com.swiggy.order_service.service;

import com.swiggy.order_service.client.MenuItemsClient;
import com.swiggy.order_service.dto.AssignedOrderDetails;
import com.swiggy.order_service.entity.Order;
import com.swiggy.order_service.entity.OrderItem;
import com.swiggy.order_service.entity.User;
import com.swiggy.order_service.exception.OrderNotFoundException;
import com.swiggy.order_service.repository.OrderRepository;
import com.swiggy.order_service.requestModel.OrderRequest;
import com.swiggy.order_service.responseModel.MenuItemResponse;
import com.swiggy.order_service.responseModel.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private MenuItemsClient menuItemsClient;



    @Override
    public OrderResponse create(OrderRequest request, User user) {
        List<Long> menuItemIds = request.getOrderItems().stream()
                .map(OrderItem::getMenuItemId).toList();

        MenuItemResponse response = menuItemsClient.getMenuList(request.getRestaurantId(),menuItemIds);
        response.setQuantity(request);
        Double totalPrice = response.calculateTotalPrice();

        Order order = new Order(user,totalPrice);
        Order savedOrder = orderRepository.save(order);
        orderItemService.create(request.getOrderItems(),savedOrder);
        AssignedOrderDetails orderDetails = savedOrder.assignDeliveryPersonnel(response);
        return new OrderResponse(order,response,orderDetails);
    }

    @Override
    public Order get(Long id) {
        return orderRepository.findById(id).orElseThrow(()-> new OrderNotFoundException(id));
    }

}
