package com.rishon.orderintegration.service;

import com.rishon.orderintegration.client.FastShipClient;
import com.rishon.orderintegration.dto.request.OrderRequest;
import com.rishon.orderintegration.dto.response.OrderResponse;
import com.rishon.orderintegration.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Application business logic. Calls the client layer when an external system must be contacted.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final FastShipClient fastShipClient;

    public OrderResponse createOrder(OrderRequest request) {
        ProductResponse product = fastShipClient.getProduct();
        log.info("Downstream product received: id={}, title={}, price={}",
                product.getId(), product.getTitle(), product.getPrice());

        return new OrderResponse(request.getOrderId(), "RECEIVED");
    }
}
