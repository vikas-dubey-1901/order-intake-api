package com.vikas.orderprocessing.api.controller;

import com.vikas.orderprocessing.api.mapper.OrderApiMapper;
import com.vikas.orderprocessing.api.request.CreateOrderRequest;
import com.vikas.orderprocessing.api.response.OrderResponse;
import com.vikas.orderprocessing.application.OrderProcessingUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderProcessingUseCase orderProcessingUseCase;
    private final OrderApiMapper orderApiMapper;

    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(
            @RequestHeader("X-Request-Id") String requestId,
            @RequestHeader("X-Correlation-Id") String correlationId,
            @RequestHeader("X-Client-Id") String clientId,
            @Valid @RequestBody CreateOrderRequest request){

        CreateOrderCommand command =  orderApiMapper.toCreateOrderCommand(
                requestId,
                correlationId,
                clientId,
                request
        );

        OrderResult result = orderProcessingUseCase.process(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderApiMapper, toCreateOrderResponse(result));

    }

    @GetMapping
    public ResponseEntity<OrderResponse> getOrderByid(
            @PathVariable UUID orderId,
            @RequestHeader("X-Correlation-Id") String correlationId
            ){

        OrderView order = orderProcessingUseCase.getOrder(orderId);

        return ResponseEntity.ok(orderApiMapper.toOrderResponse(order));
    }

    @GetMapping
    public ResponseEntity<PagedOrderResponse> searchOrders(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) OrderChannel channel,
            @RequestParam(required = false) Instant fromDate,
            @RequestParam(required = false) Instant toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        OrderSearchCriteria criteria =
                OrderSearchCriteria.builder()
                        .customerId(customerId)
                        .status(status)
                        .channel(channel)
                        .fromDate(fromDate)
                        .toDate(toDate)
                        .page(page)
                        .size(size)
                        .sort(sort)
                        .build();

        PagedOrderView result =
                orderProcessingUseCase.searchOrders(criteria);

        return ResponseEntity.ok(
                orderApiMapper.toPagedOrderResponse(result)
        );
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<CancelOrderResponse> cancelOrder(
            @PathVariable UUID orderId,
            @RequestHeader("X-Request-Id") String requestId,
            @RequestHeader("X-Correlation-Id") String correlationId
    ) {
        CancelOrderCommand command =
                new CancelOrderCommand(orderId, requestId, correlationId);

        OrderResult result =
                orderProcessingUseCase.cancel(command);

        return ResponseEntity.ok(
                orderApiMapper.toCancelOrderResponse(result)
        );
    }

    @GetMapping("/{orderId}/history")
    public ResponseEntity<OrderHistoryResponse> getOrderHistory(
            @PathVariable UUID orderId
    ) {
        OrderHistoryView history =
                orderProcessingUseCase.getOrderHistory(orderId);

        return ResponseEntity.ok(
                orderApiMapper.toOrderHistoryResponse(history)
        );
    }

    


}
