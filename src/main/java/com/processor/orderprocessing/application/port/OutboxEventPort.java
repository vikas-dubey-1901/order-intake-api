package com.processor.orderprocessing.application.port;

public interface OutboxEventPort {
    void saveEvent(String aggregateId,
                   String aggregateType,
                   String eventType,
                   String payload);
}
