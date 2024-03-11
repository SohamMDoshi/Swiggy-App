package com.swiggy.order_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.order_service.entity.Location;
import com.swiggy.order_service.entity.Order;
import com.swiggy.order_service.entity.User;
import com.swiggy.order_service.repository.UserRepository;
import com.swiggy.order_service.requestModel.OrderRequest;
import com.swiggy.order_service.responseModel.OrderResponse;
import com.swiggy.order_service.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "USER")
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @MockBean
    private UserRepository userRepository;


    @Test
    void testCreate() throws Exception{
        User user =  mock(User.class);
        OrderRequest request = mock(OrderRequest.class);
        String requestBody = objectMapper.writeValueAsString(request);

        OrderResponse response = mock(OrderResponse.class);
        String responseBody = objectMapper.writeValueAsString(response);

        when(orderService.create(any(OrderRequest.class),any(User.class))).thenReturn(response);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        mockMvc.perform(post("/order-service/users/1/orders").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseBody));

        verify(orderService,times(1)).create(any(OrderRequest.class),any(User.class));
    }

    @Test
    void testGet() throws Exception{
        User user =  mock(User.class);

        Order order = mock(Order.class);
        String responseBody = objectMapper.writeValueAsString(order);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(orderService.get(anyLong())).thenReturn(order);

        mockMvc.perform(get("/order-service/users/1/orders/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));

        verify(orderService, times(1)).get(anyLong());
    }
}
