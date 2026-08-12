package com.rishon.fastship.service;

import com.rishon.fastship.dto.request.ProductCreateRequest;
import com.rishon.fastship.dto.response.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class FastShipOrderService {

    private final AtomicLong idSequence = new AtomicLong(1001);

    public ProductResponse createProduct(ProductCreateRequest request) {
        return new ProductResponse(
                idSequence.getAndIncrement(),
                request.getTitle(),
                request.getPrice()
        );
    }
}
