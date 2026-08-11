package com.rishon.orderintegration.client;

import com.rishon.orderintegration.dto.request.ProductCreateRequest;
import com.rishon.orderintegration.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
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

    public ProductResponse createProduct(ProductCreateRequest request) {
        String url = "https://dummyjson.com/products/add";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("client-id","order-integration-service");

        /*
        HttpEntity
        │
        ├── Headers
        │    ├── Content-Type: application/json
        │    └── client-id: order-integration-service
        │
        └── Body
             ├── title: Laptop
             └── price: 1000
         */

        HttpEntity<ProductCreateRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<ProductResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                ProductResponse.class
        );

        return response.getBody();

    }
}
