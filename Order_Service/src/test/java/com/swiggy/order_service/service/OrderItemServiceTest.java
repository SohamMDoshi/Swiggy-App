package com.swiggy.order_service.service;

import com.swiggy.order_service.entity.Order;
import com.swiggy.order_service.entity.OrderItem;
import com.swiggy.order_service.repository.OrderItemRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderItemServiceTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;


    @Test
    void testCreate() {
        List<OrderItem> orderItemList = Arrays.asList(mock(OrderItem.class), mock(OrderItem.class));
        Order order = mock(Order.class);

        when(orderItemRepository.saveAll(anyList())).thenReturn(orderItemList);

        List<OrderItem> resultList = orderItemService.create(orderItemList,order);

        assertEquals(orderItemList.toString(),resultList.toString());
        verify(orderItemRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testGet() {
        OrderItem orderItem = mock(OrderItem.class);

        when(orderItemRepository.findById(anyLong())).thenReturn(Optional.of(orderItem));

        OrderItem result = orderItemService.get(1L);

        assertEquals(orderItem,result);
        verify(orderItemRepository, times(1)).findById(anyLong());
    }
}
