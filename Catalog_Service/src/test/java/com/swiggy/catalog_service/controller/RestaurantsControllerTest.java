package com.swiggy.catalog_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.catalog_service.entity.Restaurant;
import com.swiggy.catalog_service.repository.RestaurantRepository;
import com.swiggy.catalog_service.requestModels.RestaurantRequest;
import com.swiggy.catalog_service.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RestaurantsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RestaurantService restaurantService;


    @Test
    public void testCreateRestaurant() throws Exception{
        RestaurantRequest request =  mock(RestaurantRequest.class);
        String requestBody = objectMapper.writeValueAsString(request);

        Restaurant response = mock(Restaurant.class);
        String responseBody = objectMapper.writeValueAsString(response);

        when(restaurantService.create(any(RestaurantRequest.class))).thenReturn(response);

        mockMvc.perform(post("/restaurants").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseBody));

        verify(restaurantService,times(1)).create(any(RestaurantRequest.class));
    }

    @Test
    public void testGetRestaurant() throws Exception{
        Restaurant response = mock(Restaurant.class);
        String responseBody = objectMapper.writeValueAsString(response);

        when(restaurantService.get(anyLong())).thenReturn(response);

        mockMvc.perform(get("/restaurants/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));

        verify(restaurantService,times(1)).get(anyLong());
    }

    @Test
    public void testUpdateRestaurant() throws Exception {
        RestaurantRequest request = mock(RestaurantRequest.class);
        String requestBody = objectMapper.writeValueAsString(request);

        Restaurant response = mock(Restaurant.class);
        String responseBody = objectMapper.writeValueAsString(response);

        when(restaurantService.update(anyLong(),any(RestaurantRequest.class))).thenReturn(response);

        mockMvc.perform(put("/restaurants/1").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));

        verify(restaurantService,times(1)).update(anyLong(),any(RestaurantRequest.class));
    }

}
