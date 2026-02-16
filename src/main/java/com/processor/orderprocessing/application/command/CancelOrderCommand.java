package com.processor.orderprocessing.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CancelOrderCommand {

    private UUID orderId;
    private String requestId;
    private String correlationId;

}
