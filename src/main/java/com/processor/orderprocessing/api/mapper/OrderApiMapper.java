package com.processor.orderprocessing.api.mapper;

import com.processor.orderprocessing.api.request.CreateOrderRequest;
import com.processor.orderprocessing.api.request.OrderItemRequest;
import com.processor.orderprocessing.api.response.*;
import com.vikas.orderprocessing.api.response.*;
import com.processor.orderprocessing.domain.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderApiMapper {

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
                request.getOrderType(),
                request.getChannel(),
                request.getCustomerId(),
                mapItems(request.getItems()),
                request.getCurrency(),
                request.getMetadata()
        );
    }

    public CreateOrderResponse toCreateOrderResponse(OrderResult result) {
        // implementation
    }

    public OrderResponse toOrderResponse(OrderView view) {
        return OrderResponse.builder()
                .orderId(view.getOrderId())
                .orderType(view.getOrderType())
                .channel(view.getChannel())
                .customerId(view.getCustomerId())
                .status(view.getStatus())
                .currency(view.getCurrency())
                .items(mapItemResponses(view.getItems()))
                .metadata(view.getMetadata())
                .createdAt(view.getCreatedAt())
                .updatedAt(view.getUpdatedAt())
                .build();
    }

    public PagedOrderResponse toPagedOrderResponse(PagedOrderView view) {
        return PagedOrderResponse.builder()
                .page(view.getPage())
                .size(view.getSize())
                .totalElements(view.getTotalElements())
                .orders(
                        view.getOrders().stream()
                                .map(this::toOrderSummary)
                                .toList()
                )
                .build();
    }

    public CancelOrderResponse toCancelOrderResponse(OrderResult result) {
        return new CancelOrderResponse(
                result.getOrderId(),
                result.getStatus().name(),
                result.getMessage()
        );
    }

    public OrderHistoryResponse toOrderHistoryResponse(OrderHistoryView view) {
        return OrderHistoryResponse.builder()
                .orderId(view.getOrderId())
                .history(
                        view.getHistory().stream()
                                .map(this::toStatusEntry)
                                .toList()
                )
                .build();
    }

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

    private OrderStatusEntry toStatusEntry(OrderStatusView view) {
        return OrderStatusEntry.builder()
                .status(view.getStatus())
                .timestamp(view.getTimestamp())
                .build();
    }



}
