package com.swiggy.order_service.controller;

import com.swiggy.order_service.entity.Order;
import com.swiggy.order_service.entity.User;
import com.swiggy.order_service.exception.UserNotFoundException;
import com.swiggy.order_service.repository.UserRepository;
import com.swiggy.order_service.requestModel.OrderRequest;
import com.swiggy.order_service.responseModel.OrderResponse;
import com.swiggy.order_service.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Validated
@RestController
@RequestMapping("/order-service/users/{userId}/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping
    public ResponseEntity<OrderResponse> create(@PathVariable Long userId, @Valid@RequestBody OrderRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException(username));
        OrderResponse response = orderService.create(request,user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<Order> get(@PathVariable Long userId,@PathVariable Long orderId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException(username));
        Order order = orderService.get(orderId);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }
}
