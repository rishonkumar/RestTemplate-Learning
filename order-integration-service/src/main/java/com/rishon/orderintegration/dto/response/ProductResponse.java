package com.rishon.orderintegration.dto.response;

import lombok.Data;

/**
 * Downstream product fields mapped from DummyJSON / FastShip product API.
 */
@Data
public class ProductResponse {

    private Long id;
    private String title;
    private Double price;
}
