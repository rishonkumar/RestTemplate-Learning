package com.rishon.fastship.controller;

import com.rishon.fastship.dto.request.ProductCreateRequest;
import com.rishon.fastship.dto.response.ProductResponse;
import com.rishon.fastship.service.FastShipOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class FastShipOrderController {

    private static final String EXPECTED_AUTHORIZATION = "Bearer fastship-access-token";

    private final FastShipOrderService fastShipOrderService;

    private final AtomicBoolean firstRequest = new AtomicBoolean(true);

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody ProductCreateRequest request) {

        if (authorization == null || !EXPECTED_AUTHORIZATION.equals(authorization)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (firstRequest.compareAndSet(true, false)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        ProductResponse response = fastShipOrderService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
