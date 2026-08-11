package com.rishon.orderintegration.service;

import com.rishon.orderintegration.dto.request.OrderRequest;
import com.rishon.orderintegration.dto.response.OrderResponse;
import org.springframework.stereotype.Service;

/**
 * Application business logic. Calls the client layer when an external system must be contacted.
 */
@Service
public class OrderService {

    public OrderResponse createOrder(OrderRequest request) {
        return new OrderResponse(request.getOrderId(), "RECEIVED");
    }
}
