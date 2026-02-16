package com.processor.orderprocessing.application.view;

import com.processor.orderprocessing.domain.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class OrderItemView {

    private String productId;
    private int quantity;
    private BigDecimal unitPrice;

    public static OrderItemView from(OrderItem item) {
        return new OrderItemView(
                item.getProductId(),
                item.getQuantity(),
                item.getUnitPrice()
        );
    }
}
