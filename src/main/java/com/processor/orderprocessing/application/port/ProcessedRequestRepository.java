package com.processor.orderprocessing.application.port;

public interface ProcessedRequestRepository {

    boolean exists(String requestId);

    void save(String requestId);
}

