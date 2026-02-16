package com.processor.orderprocessing.messaging.mapper;

import com.processor.orderprocessing.application.command.CreateOrderCommand;
import com.processor.orderprocessing.domain.model.OrderItem;
import com.processor.orderprocessing.messaging.event.OrderCreatedEvent;
import com.processor.orderprocessing.messaging.event.OrderItemPayload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderEventMapper {

    public CreateOrderCommand toCommand(OrderCreatedEvent event){
        return new CreateOrderCommand(
                event.getEventId(),          // requestId equivalent
                event.getCorrelationId(),
                "KAFKA",                     // source identifier
                event.getChannel(),
                event.getOrderType(),
                event.getCustomerId(),
                mapItems(event.getItems()),
                event.getCurrency(),
                java.util.Map.of()
        );
    }

    private List<OrderItem> mapItems(List<OrderItemPayload> payloads) {
        return payloads.stream()
                .map(p -> new OrderItem(
                        p.getProductId(),
                        p.getQuantity(),
                        p.getUnitPrice()
                ))
                .toList();
    }
}
