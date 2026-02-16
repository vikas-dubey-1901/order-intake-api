package com.processor.orderprocessing.api.response;

import com.processor.orderprocessing.application.result.OrderResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateOrderResponse {

    private UUID orderId;
    private String status;
    private String message;

    public CreateOrderResponse toCreateOrderResponse(OrderResult result) {

        return new CreateOrderResponse(
                result.getOrderId(),
                result.getStatus().name(),
                result.getMessage()
        );
    }

}
