package com.swiggy.order_service.service;

import com.swiggy.order_service.entity.Order;
import com.swiggy.order_service.entity.OrderItem;
import com.swiggy.order_service.requestModel.OrderRequest;

import java.util.List;

public interface OrderItemService {

    OrderItem get(Long id);
    List<OrderItem> create(List<OrderItem> orderItems, Order order);
    String delete(Long id);
}
