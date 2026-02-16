package com.processor.orderprocessing.application;

import com.processor.orderprocessing.application.command.CancelOrderCommand;
import com.processor.orderprocessing.application.command.CreateOrderCommand;
import com.processor.orderprocessing.application.result.OrderResult;
import com.processor.orderprocessing.application.view.OrderHistoryView;
import com.processor.orderprocessing.application.view.OrderView;
import com.processor.orderprocessing.application.view.PagedOrderView;

import java.util.UUID;

public interface OrderProcessingUseCase {

    OrderResult process(CreateOrderCommand command);

    OrderView getOrder(UUID orderId);

    PagedOrderView searchOrders(OrderSearchCriteria criteria);

    OrderResult cancel(CancelOrderCommand command);

    OrderHistoryView getOrderHistory(UUID orderId);
}
