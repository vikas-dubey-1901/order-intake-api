package com.processor.orderprocessing.application.view;

import com.processor.orderprocessing.domain.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class OrderHistoryView {

    private UUID orderId;
    private List<OrderStatusEntryView> history;

    public static OrderHistoryView from(Order order) {

        List<OrderStatusEntryView> entries =
                order.getStatusHistory()
                        .stream()
                        .map(OrderStatusEntryView::from)
                        .toList();

        return new OrderHistoryView(
                order.getId(),
                entries
        );
    }
}
