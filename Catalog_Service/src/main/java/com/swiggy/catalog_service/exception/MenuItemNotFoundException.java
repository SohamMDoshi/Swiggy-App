package com.swiggy.catalog_service.exception;

public class MenuItemNotFoundException extends RuntimeException{

    public MenuItemNotFoundException(Long id) {
        super("Menu item is not found with id "+ id);
    }
}
