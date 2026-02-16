package com.processor.orderprocessing.application;

import com.processor.orderprocessing.domain.model.Order;
import com.processor.orderprocessing.outbox.repository.OutboxRepository;
import com.processor.orderprocessing.persistence.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderProcessingService implements OrderProcessingUseCase{
    private final OrderRepository orderRepository;
    private final ProcessedRequestRepository processedRequestRepository;
    private final OrderValidator orderValidator;
    private final OutboxRepository outboxRepository;

    @Override
    public OrderResult process(CreateOrderCommand command) {

        // 1. Idempotency
        if (processedRequestRepository.exists(command.getRequestId())) {
            throw new DuplicateRequestException(command.getRequestId());
        }

        // 2. Convert command → domain
        Order order = Order.create(
                command.getOrderType(),
                command.getChannel(),
                command.getCustomerId(),
                command.getItems(),
                command.getCurrency(),
                command.getMetadata()
        );

        // 3. Business validation
        orderValidator.validate(order);

        // 4. Persist order
        Order savedOrder = orderRepository.save(order);

        // 5. Mark request as processed
        processedRequestRepository.save(command.getRequestId());

        // 6. Create outbox event
        outboxRepository.save(
                OutboxEvent.orderReceived(savedOrder)
        );

        // 7. Return result
        return OrderResult.success(
                savedOrder.getId(),
                savedOrder.getStatus(),
                "Order registered successfully"
        );
    }

    @Override
    @Transactional(readOnly = true)
    public OrderView getOrder(UUID orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        return OrderView.from(order);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedOrderView searchOrders(OrderSearchCriteria criteria) {

        Page<Order> page = orderRepository.search(criteria);

        return PagedOrderView.from(page);
    }

    @Override
    public OrderResult cancel(CancelOrderCommand command) {

        if (processedRequestRepository.exists(command.getRequestId())) {
            throw new DuplicateRequestException(command.getRequestId());
        }

        Order order = orderRepository.findById(command.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException(command.getOrderId()));

        order.cancel(); // domain enforces valid state transitions

        Order updatedOrder = orderRepository.save(order);

        processedRequestRepository.save(command.getRequestId());

        outboxRepository.save(
                OutboxEvent.orderCancelled(updatedOrder)
        );

        return OrderResult.success(
                updatedOrder.getId(),
                updatedOrder.getStatus(),
                "Order cancelled successfully"
        );
    }

    @Override
    @Transactional(readOnly = true)
    public OrderHistoryView getOrderHistory(UUID orderId) {

        Order order = orderRepository.findByIdWithHistory(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        return OrderHistoryView.from(order);
    }


}
