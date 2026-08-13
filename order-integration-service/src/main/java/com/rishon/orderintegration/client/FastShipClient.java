package com.rishon.orderintegration.client;

import com.rishon.orderintegration.auth.OAuthService;
import com.rishon.orderintegration.dto.request.ProductCreateRequest;
import com.rishon.orderintegration.dto.response.ProductResponse;
import com.rishon.orderintegration.exception.DownstreamServiceException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * Downstream FastShip API client. Constructs and executes HTTP calls via
 * RestTemplate.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FastShipClient {

    private static final String PRODUCT_URL = "http://localhost:8081/api/products";
    String url = "http://localhost:8081/api/products";

    private final RestTemplate restTemplate;

    private final OAuthService oAuthService;

    public ProductResponse getProduct() {
        log.info("Calling downstream API: {}", PRODUCT_URL);

        HttpEntity<Void> entity = new HttpEntity<>(null);

        ResponseEntity<ProductResponse> response = restTemplate.exchange(
                PRODUCT_URL,
                HttpMethod.GET,
                entity,
                ProductResponse.class);

        log.info("Downstream API response status: {}", response.getStatusCode());

        return response.getBody();
    }

    /*
     * HttpEntity
     * │
     * ├── Headers
     * │ ├── Content-Type: application/json
     * │ └── client-id: order-integration-service
     * │
     * └── Body
     * ├── title: Laptop
     * └── price: 1000
     * 
     * 
     * After adding token it looks like this
     * 
     * HttpEntity
     * │
     * ├── Headers
     * │ ├── Authorization: Bearer abc123
     * │ ├── Content-Type: application/json
     * │ └── client-id: order-integration-service
     * │
     * 
     * └── Body
     * ├── title
     * └── price
     */

    public ProductResponse createProduct(
            ProductCreateRequest request) {

        String accessToken = oAuthService.fetchAccessToken();

        try {
            return callCreateProduct(
                    request,
                    accessToken);

        } catch (HttpClientErrorException.Unauthorized ex) {

            log.warn(
                    "Received 401 from FastShip. Refreshing token and retrying.");

            String newAccessToken = oAuthService.fetchAccessToken();

            try {
                return callCreateProduct(request, newAccessToken);
            } catch (HttpClientErrorException ex2) {

                throw new DownstreamServiceException(
                        "FastShip API failed after token refresh",
                        ex2);
            }

        } catch (HttpClientErrorException ex) {
            throw new DownstreamServiceException(
                    "FastShip returned HTTP error: "
                            + ex.getStatusCode(),
                    ex);
        } catch (ResourceAccessException ex) {
            throw new DownstreamServiceException(
                    "Unable to communicate with FastShip",
                    ex);
        }
    }

    private ProductResponse callCreateProduct(
            ProductCreateRequest request,
            String accessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("client-id", "order-integration-service");
        HttpEntity<ProductCreateRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ProductResponse> response = restTemplate.exchange(
                PRODUCT_URL,
                HttpMethod.POST,
                entity,
                ProductResponse.class);

        return response.getBody();

    }
}
