package com.processor.orderprocessing.messaging.consumer;

import com.processor.orderprocessing.application.command.CreateOrderCommand;
import com.processor.orderprocessing.application.OrderProcessingUseCase;
import com.processor.orderprocessing.messaging.event.OrderCreatedEvent;
import com.processor.orderprocessing.messaging.mapper.OrderEventMapper;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedConsumer {

    private final OrderProcessingUseCase orderProcessingUseCase;
    private final OrderEventMapper eventMapper;

    @KafkaListener(
            topics = "order-created",
            groupId = "order-service-group"
    )
    public void consume(
            @Payload OrderCreatedEvent event,
            @Header(KafkaHeaders.RECEIVED_KEY) String key
            ){

        try{
            CreateOrderCommand command = eventMapper.toCommand(event);

            orderProcessingUseCase.process(command);
        }
        catch (DuplicateRequestException ex) {
            log.info("Duplicate event ignored: {}", event.getEventId());
        } catch (Exception ex) {
            log.error("Error processing event {}", event.getEventId(), ex);
            throw ex; // Let Kafka retry
        }
    }
}

