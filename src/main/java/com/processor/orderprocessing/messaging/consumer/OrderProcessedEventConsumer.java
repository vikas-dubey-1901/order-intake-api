package com.processor.orderprocessing.messaging.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.processor.orderprocessing.application.OrderProcessingUseCase;
import com.processor.orderprocessing.domain.domainEnum.OrderStatus;
import com.processor.orderprocessing.messaging.event.KafkaTopics;
import com.processor.orderprocessing.messaging.event.OrderProcessedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderProcessedEventConsumer {

    private final OrderProcessingUseCase orderProcessingUseCase;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = KafkaTopics.ORDER_PROCESSED,
            groupId = "order-service-group"
    )
    public void handle(String payload ){

        OrderProcessedEvent event = null;

        try {
            event = objectMapper.readValue(payload , OrderProcessedEvent.class);

            log.info("Received ORDER_PROCESSED event {}", event);

            orderProcessingUseCase.updateOrderStatus(
                    event.getOrderId(),
                    OrderStatus.valueOf(event.getStatus())
            );

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}