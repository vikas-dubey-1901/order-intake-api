package com.processor.orderprocessing.api.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequest {

    @NotNull
    private String productId;

    @Min(value = 1 , message = "Quantity must be at least 1")
    @DecimalMin(value = "0.0" , inclusive = false , message = "Unit price must be greater than zero")
    private int quantity;

    private BigDecimal UnitPrice;

}
