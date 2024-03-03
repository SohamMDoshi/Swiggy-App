package com.swiggy.order_service.controller;

import com.swiggy.order_service.entity.User;
import com.swiggy.order_service.repository.UserRepository;
import com.swiggy.order_service.requestModel.UserRegistrationRequest;
import com.swiggy.order_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        User users = userService.add(request.getUsername(), request.getPassword());
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }

}
