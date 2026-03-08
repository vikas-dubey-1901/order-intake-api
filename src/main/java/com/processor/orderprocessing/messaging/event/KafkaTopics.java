package com.processor.orderprocessing.messaging.event;

public final class KafkaTopics {

    private KafkaTopics() {}

    public static final String ORDER_RECEIVED = "order-received";
    public static final String ORDER_PROCESSED = "order-processed";

}