package com.swiggy.order_service.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.order_service.entity.Location;
import com.swiggy.order_service.entity.User;
import com.swiggy.order_service.requestModel.UserRegistrationRequest;
import com.swiggy.order_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void testRegisterUser() throws Exception {
        UserRegistrationRequest request =  new UserRegistrationRequest("TestUser","pass", new Location(18.65,73.78));
        String requestBody = objectMapper.writeValueAsString(request);

        User response = mock(User.class);
        String responseBody = objectMapper.writeValueAsString(response);

        when(userService.add(anyString(),anyString(),any(Location.class))).thenReturn(response);

        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseBody));

        verify(userService,times(1)).add(anyString(), anyString(),any(Location.class));
    }
}
