package com.swiggy.order_service.service;

import com.swiggy.order_service.entity.User;

public interface UserService {
    User add(String username,String password);
}
