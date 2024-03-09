package com.swiggy.order_service.client;

import com.swiggy.order_service.requestModel.OrderRequest;
import com.swiggy.order_service.responseModel.MenuItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class MenuItemsClient {

    @Autowired
    private RestTemplate restTemplate;


    public List<MenuItemResponse> getMenuListAndRestaurant(Map<Long, List<Long>> restaurantIdToMenuIdsMap) {
        String url = "http://localhost:8080/catalog-service/restaurants/menu-items";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<Long, List<Long>>> requestEntity = new HttpEntity<>(restaurantIdToMenuIdsMap, headers);
        ResponseEntity<List<MenuItemResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<List<MenuItemResponse>>() {}
        );
        return response.getBody();
    }

    public MenuItemResponse getMenuList(List<Long> menuItemIds, OrderRequest request) {
        String url = "http://localhost:8080/catalog-service/restaurants/"+request.getRestaurantId()+"/menu-items/specific-list";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<Long>> requestEntity = new HttpEntity<>(menuItemIds, headers);

        ResponseEntity<MenuItemResponse> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                MenuItemResponse.class
        );
        return responseEntity.getBody();
    }
}
