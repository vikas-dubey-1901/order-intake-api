package com.processor.orderprocessing.messaging.event;

import java.util.UUID;

public record OrderReceivedEvent(
        String orderId,
        String customerId,
        String status
) {}
