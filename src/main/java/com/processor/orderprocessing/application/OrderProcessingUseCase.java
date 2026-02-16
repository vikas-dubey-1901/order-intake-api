package com.processor.orderprocessing.application;

import com.processor.orderprocessing.api.mapper.CreateOrderCommand;

import java.util.UUID;

public interface OrderProcessingUseCase {

    OrderResult process(CreateOrderCommand command);

    OrderView getOrder(UUID orderId);

    PagedOrderView searchOrders(OrderSearchCriteria criteria);

    OrderResult cancel(CancelOrderCommand command);

    OrderHistoryView getOrderHistory(UUID orderId);
}
