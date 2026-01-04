package com.vikas.orderprocessing.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CancelOrderResponse {

    private UUID orderId;
    private String status;
    private String message;
}
