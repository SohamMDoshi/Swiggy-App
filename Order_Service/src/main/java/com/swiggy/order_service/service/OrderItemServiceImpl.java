package com.swiggy.order_service.service;

import com.swiggy.order_service.entity.Order;
import com.swiggy.order_service.entity.OrderItem;
import com.swiggy.order_service.exception.OrderItemNotFoundException;
import com.swiggy.order_service.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderItemServiceImpl implements OrderItemService{

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItem get(Long id) {
        return orderItemRepository.findById(id).orElseThrow(()-> new OrderItemNotFoundException(id));
    }

    @Override
    public List<OrderItem> create(List<OrderItem> orderItems, Order order) {
        for (OrderItem item : orderItems) item.setOrder(order);
        return orderItemRepository.saveAll(orderItems);
    }

    @Override
    public String delete(Long id) {
        orderItemRepository.findById(id).orElseThrow(()-> new OrderItemNotFoundException(id));
        orderItemRepository.deleteById(id);
        return "Oder item got successfully deleted with id " + id;
    }
}
