package com.swiggy.order_service.client;

import com.swiggy.order_service.responseModel.MenuItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MenuItemsClient {

    @Autowired
    private RestTemplate restTemplate;


    public MenuItemResponse getMenuList(Long restaurantId, List<Long> menuItemIds) {
        String url = "http://localhost:8080/catalog-service/restaurants/" + restaurantId + "/menu-items/specific-list";

        // Convert the list of menuItemIds to a comma-separated string
        String menuItemsIdsString = menuItemIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        // Build the URL with the query parameter
        String fullUrl = UriComponentsBuilder.fromUriString(url)
                .queryParam("menuItemsIds", menuItemsIdsString)
                .build()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<MenuItemResponse> responseEntity = restTemplate.exchange(
                fullUrl,
                HttpMethod.GET,
                requestEntity,
                MenuItemResponse.class
        );

        return responseEntity.getBody();
    }
}
