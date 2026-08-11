package com.rishon.orderintegration.client;

import com.rishon.orderintegration.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Downstream FastShip API client. Constructs and executes HTTP calls via RestTemplate.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FastShipClient {

    private static final String PRODUCT_URL = "https://dummyjson.com/products/1";

    private final RestTemplate restTemplate;

    public FastShipClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProductResponse getProduct() {
        log.info("Calling downstream API: {}", PRODUCT_URL);

        HttpEntity<Void> entity = new HttpEntity<>(null);

        ResponseEntity<ProductResponse> response = restTemplate.exchange(
                PRODUCT_URL,
                HttpMethod.GET,
                entity,
                ProductResponse.class
        );

        log.info("Downstream API response status: {}", response.getStatusCode());

        return response.getBody();
    }
}
