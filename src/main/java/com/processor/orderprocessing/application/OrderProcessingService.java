package com.processor.orderprocessing.application;

import com.processor.orderprocessing.application.command.CancelOrderCommand;
import com.processor.orderprocessing.application.command.CreateOrderCommand;
import com.processor.orderprocessing.application.event.OutboxEvent;
import com.processor.orderprocessing.application.exception.DuplicateRequestException;
import com.processor.orderprocessing.application.port.OrderRepository;
import com.processor.orderprocessing.application.port.ProcessedRequestRepository;
import com.processor.orderprocessing.application.port.OutboxRepository;
import com.processor.orderprocessing.application.result.OrderResult;
import com.processor.orderprocessing.application.view.OrderHistoryView;
import com.processor.orderprocessing.application.view.OrderView;
import com.processor.orderprocessing.application.view.PagedOrderView;
import com.processor.orderprocessing.domain.exception.OrderNotFoundException;
import com.processor.orderprocessing.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderProcessingService implements OrderProcessingUseCase {

    private final OrderRepository orderRepository;
    private final ProcessedRequestRepository processedRequestRepository;
    private final OrderValidator orderValidator;
    private final OutboxRepository outboxRepository;

    @Override
    public OrderResult process(CreateOrderCommand command) {

        if (processedRequestRepository.exists(command.getRequestId())) {
            throw new DuplicateRequestException(command.getRequestId());
        }

        Order order = Order.create(
                command.getOrderType(),
                command.getChannel(),
                command.getCustomerId(),
                command.getItems(),
                command.getCurrency());

        orderValidator.validate(order);

        Order savedOrder = orderRepository.save(order);

        processedRequestRepository.save(command.getRequestId());

        outboxRepository.save(
                OutboxEvent.orderReceived(savedOrder)
        );

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
