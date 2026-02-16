package com.processor.orderprocessing.domain.model;

import com.processor.orderprocessing.domain.exception.OrderValidationException;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public final class OrderItem {
    private final String productId;
    private final int quantity;
    private final BigDecimal unitPrice;

    public OrderItem(String productId, int quantity, BigDecimal unitPrice) {

        if (productId == null || productId.isBlank()) {
            throw new OrderValidationException("ProductId must not be blank");
        }
        if (quantity <= 0) {
            throw new OrderValidationException("Quantity must be greater than zero");
        }
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new OrderValidationException("Unit price must be greater than zero");
        }

        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public BigDecimal lineTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
