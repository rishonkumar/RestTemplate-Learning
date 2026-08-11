package com.rishon.orderintegration.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Incoming order request payload from our client.
 */
@Data
public class OrderRequest {

    @NotBlank
    private String orderId;

    @NotBlank
    private String customerName;

    @NotBlank
    private String product;

    @NotNull
    @Min(1)
    private Integer quantity;
}
