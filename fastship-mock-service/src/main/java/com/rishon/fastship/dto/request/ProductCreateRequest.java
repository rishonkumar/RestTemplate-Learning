package com.rishon.fastship.dto.request;

import lombok.Data;

@Data
public class ProductCreateRequest {

    private String title;
    private Double price;
}
