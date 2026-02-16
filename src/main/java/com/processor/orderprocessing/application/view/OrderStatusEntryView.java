package com.processor.orderprocessing.application.view;

import com.processor.orderprocessing.domain.model.OrderStatusHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class OrderStatusEntryView {

    private String status;
    private Instant changedAt;

    public static OrderStatusEntryView from(OrderStatusHistory history) {
        return new OrderStatusEntryView(
                history.getStatus().name(),
                history.getChangedAt()
        );
    }
}
