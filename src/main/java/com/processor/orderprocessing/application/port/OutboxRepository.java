package com.processor.orderprocessing.application.port;

import com.processor.orderprocessing.application.event.OutboxEvent;

public interface OutboxRepository {

    void save(OutboxEvent event);
}

