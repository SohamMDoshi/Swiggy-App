package com.swiggy.order_service.service;

import com.swiggy.order_service.entity.Order;
import com.swiggy.order_service.entity.OrderItem;
import com.swiggy.order_service.entity.User;
import com.swiggy.order_service.exception.OrderNotFoundException;
import com.swiggy.order_service.repository.OrderRepository;
import com.swiggy.order_service.requestModel.OrderRequest;
import com.swiggy.order_service.responseModel.MenuItemResponse;
import com.swiggy.order_service.responseModel.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.swiggy.order_service.responseModel.MenuItemResponse.setQuantity;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderItemService orderItemService;

    @Override
    public OrderResponse create(List<OrderRequest> requests, User user) {
        Map<Long, List<Long>> restaurantIdToMenuIdsMap = requests.stream()
                .collect(Collectors.toMap(
                        OrderRequest::getRestaurantId,
                        orderRequest -> orderRequest.getOrderItems().stream()
                                .map(OrderItem::getMenuItemId)
                                .collect(Collectors.toList())
                ));

        String url = "http://localhost:8080/catalog-service/restaurants/menu-items";
        ResponseEntity<List<MenuItemResponse>> response = getListResponseEntity(restaurantIdToMenuIdsMap, url);

        List<MenuItemResponse> menuItemsResponse = response.getBody();
        System.out.println(menuItemsResponse);
        setQuantity(requests, menuItemsResponse);

        Double totalPrice = 0.0;
        for(MenuItemResponse res : menuItemsResponse) {
            totalPrice+= res.calculateTotalPrice();
        }

        Order order = new Order(user,totalPrice);
        Order savedOrder = orderRepository.save(order);

        for(OrderRequest request : requests) orderItemService.create(request.getOrderItems(),savedOrder);
        return new OrderResponse(order,menuItemsResponse);
    }

    private ResponseEntity<List<MenuItemResponse>> getListResponseEntity(Map<Long, List<Long>> restaurantIdToMenuIdsMap, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<Long, List<Long>>> requestEntity = new HttpEntity<>(restaurantIdToMenuIdsMap, headers);

        return restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<MenuItemResponse>>() {});
    }



    @Override
    public Order get(Long id) {
        return orderRepository.findById(id).orElseThrow(()-> new OrderNotFoundException(id));
    }
}
