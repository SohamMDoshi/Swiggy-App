package com.swiggy.catalog_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.catalog_service.entity.MenuItem;
import com.swiggy.catalog_service.service.MenuItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MenuItemsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MenuItemService menuItemService;

    @Test
    public void testCreate() throws Exception {
        MenuItem request =  mock(MenuItem.class);
        String requestBody = objectMapper.writeValueAsString(request);

        MenuItem response = mock(MenuItem.class);
        String responseBody = objectMapper.writeValueAsString(response);

        when(menuItemService.addNew(anyLong(),any(MenuItem.class))).thenReturn(response);

        mockMvc.perform(post("/restaurants/1/menu-items").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseBody));

        verify(menuItemService,times(1)).addNew(anyLong(),any(MenuItem.class));
    }

    @Test
    public void testGetAll() throws Exception {
        List<MenuItem> response = Arrays.asList(mock(MenuItem.class),mock(MenuItem.class));
        String responseBody = objectMapper.writeValueAsString(response);

        when(menuItemService.getAll(anyLong())).thenReturn(response);

        mockMvc.perform(get("/restaurants/1/menu-items").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));

        verify(menuItemService,times(1)).getAll(anyLong());
    }
}
