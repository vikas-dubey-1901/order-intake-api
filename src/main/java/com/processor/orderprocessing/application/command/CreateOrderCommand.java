package com.processor.orderprocessing.application.command;

import com.processor.orderprocessing.domain.domainEnum.OrderChannel;
import com.processor.orderprocessing.domain.domainEnum.OrderType;
import com.processor.orderprocessing.domain.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class CreateOrderCommand {

    private String requestId;
    private String correlationId;
    private String clientId;

    private OrderChannel channel;
    OrderType orderType;
    private String customerId;
    private List<OrderItem> items;
    private String currency;
    private Map<String, String> metadata;

}
