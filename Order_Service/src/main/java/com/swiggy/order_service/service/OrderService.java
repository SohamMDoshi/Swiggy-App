package com.swiggy.order_service.service;

import com.swiggy.order_service.entity.Order;
import com.swiggy.order_service.entity.User;
import com.swiggy.order_service.requestModel.OrderRequest;
import com.swiggy.order_service.responseModel.OrderResponse;

public interface OrderService {

    OrderResponse create(OrderRequest request, User user);
    Order get(Long id);

}
