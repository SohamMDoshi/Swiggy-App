package com.swiggy.order_service.service;

import com.swiggy.order_service.client.MenuItemsClient;
import com.swiggy.order_service.entity.Order;
import com.swiggy.order_service.entity.OrderItem;
import com.swiggy.order_service.entity.User;
import com.swiggy.order_service.repository.OrderRepository;
import com.swiggy.order_service.repository.UserRepository;
import com.swiggy.order_service.requestModel.OrderRequest;
import com.swiggy.order_service.responseModel.MenuItemResponse;
import com.swiggy.order_service.responseModel.OrderResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MenuItemsClient menuItemsClient;

    @Mock
    private OrderItemService orderItemService;

    @InjectMocks
    private OrderServiceImpl orderService;


    @Test
    void testCreate() {
        User user = mock(User.class);
        OrderRequest request = mock(OrderRequest.class);
        Order order = mock(Order.class);
        List<OrderItem> orderItems = Arrays.asList(mock(OrderItem.class),mock(OrderItem.class));
        MenuItemResponse response = mock(MenuItemResponse.class);

        when(menuItemsClient.getMenuList(anyLong(),anyList())).thenReturn(response);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderItemService.create(anyList(),any(Order.class))).thenReturn(orderItems);

        OrderResponse orderResponse = orderService.create(request,user);

        verify(menuItemsClient, times(1)).getMenuList(anyLong(),anyList());
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderItemService, times(1)).create(anyList(),any(Order.class));
    }

    @Test
    void testGet() {
        Order order = mock(Order.class);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        Order result = orderService.get(1L);

        assertEquals(order,result);
        verify(orderRepository,times(1)).findById(anyLong());
    }
}
