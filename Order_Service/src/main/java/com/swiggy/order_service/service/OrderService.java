package com.swiggy.order_service.service;

import com.swiggy.order_service.entity.Order;
import com.swiggy.order_service.entity.User;
import com.swiggy.order_service.requestModel.OrderRequest;
import com.swiggy.order_service.responseModel.MultipleRestaurantOrderResponse;
import com.swiggy.order_service.responseModel.SingleRestaurantOrderResponse;

import java.util.List;

public interface OrderService {

    SingleRestaurantOrderResponse create(OrderRequest request, User user);

    MultipleRestaurantOrderResponse create(List<OrderRequest> request, User user);

    Order get(Long id);

}
