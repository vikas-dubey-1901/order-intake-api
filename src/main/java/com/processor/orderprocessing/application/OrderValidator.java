package com.processor.orderprocessing.application;

import com.processor.orderprocessing.domain.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {

    public void validate(Order order) {

        if (order.totalAmount().signum() <= 0) {
            throw new IllegalArgumentException("Order total must be greater than zero");
        }

        // Add real business validation later
    }
}
