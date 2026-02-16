package com.processor.orderprocessing.api.mapper;

import com.processor.orderprocessing.api.request.CreateOrderRequest;
import com.processor.orderprocessing.api.request.OrderItemRequest;
import com.processor.orderprocessing.api.response.*;
import com.processor.orderprocessing.application.command.CreateOrderCommand;
import com.processor.orderprocessing.application.result.OrderResult;
import com.processor.orderprocessing.application.view.*;
import com.processor.orderprocessing.domain.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderApiMapper {

    /* ============================
       REQUEST → COMMAND
       ============================ */

    public CreateOrderCommand toCreateOrderCommand(
            String requestId,
            String correlationId,
            String clientId,
            CreateOrderRequest request
    ) {
        return new CreateOrderCommand(
                requestId,
                correlationId,
                clientId,
                request.getChannel(),
                request.getOrderType(),
                request.getCustomerId(),
                mapItems(request.getItems()),
                request.getCurrency(),
                request.getMetadata()
        );
    }

    /* ============================
       RESULT → RESPONSE
       ============================ */

    public CreateOrderResponse toCreateOrderResponse(OrderResult result) {
        return new CreateOrderResponse(
                result.getOrderId(),
                result.getStatus().name(),
                result.getMessage()
        );
    }

    public CancelOrderResponse toCancelOrderResponse(OrderResult result) {
        return new CancelOrderResponse(
                result.getOrderId(),
                result.getStatus().name(),
                result.getMessage()
        );
    }

    /* ============================
       VIEW → RESPONSE
       ============================ */

    public OrderResponse toOrderResponse(OrderView view) {
        return OrderResponse.builder()
                .orderId(view.getOrderId())
                .orderType(view.getOrderType())
                .channel(view.getChannel())
                .customerId(view.getCustomerId())
                .status(view.getStatus())
                .currency(view.getCurrency())
                .items(mapItemResponses(view.getItems()))
                .createdAt(view.getCreatedAt())
                .updatedAt(view.getUpdatedAt())
                .build();
    }

    public PagedOrderResponse toPagedOrderResponse(PagedOrderView view) {
        return PagedOrderResponse.builder()
                .page(view.getPage())
                .size(view.getSize())
                .totalElements(view.getTotalElements())
                .totalPages(view.getTotalPages())
                .orders(
                        view.getOrders().stream()
                                .map(this::toOrderSummary)
                                .toList()
                )
                .build();
    }

    public OrderHistoryResponse toOrderHistoryResponse(
            OrderHistoryView view
    ) {
        return OrderHistoryResponse.builder()
                .orderId(view.getOrderId())
                .history(
                        view.getHistory().stream()
                                .map(this::toStatusEntry)
                                .toList()
                )
                .build();
    }

    /* ============================
       PRIVATE HELPERS
       ============================ */

    private OrderSummaryResponse toOrderSummary(OrderSummaryView view) {
        return OrderSummaryResponse.builder()
                .orderId(view.getOrderId())
                .status(view.getStatus())
                .customerId(view.getCustomerId())
                .createdAt(view.getCreatedAt())
                .build();
    }

    private List<OrderItem> mapItems(List<OrderItemRequest> items) {
        return items.stream()
                .map(item -> new OrderItem(
                        item.getProductId(),
                        item.getQuantity(),
                        item.getUnitPrice()
                ))
                .toList();
    }

    private List<OrderItemResponse> mapItemResponses(List<OrderItemView> items) {
        return items.stream()
                .map(item -> OrderItemResponse.builder()
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .build())
                .toList();
    }

    private OrderStatusEntryResponse toStatusEntry(
            OrderStatusEntryView view
    ) {
        return OrderStatusEntryResponse.builder()
                .status(view.getStatus())
                .timestamp(view.getChangedAt())
                .build();
    }
}
