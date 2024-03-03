package com.swiggy.order_service.exception;

public class OrderItemNotFoundException extends RuntimeException{

    public OrderItemNotFoundException (Long id) {
        super("Order item not found with id " + id);
    }
}
