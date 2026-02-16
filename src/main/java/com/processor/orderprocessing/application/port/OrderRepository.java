package com.processor.orderprocessing.application.port;

import com.processor.orderprocessing.application.OrderSearchCriteria;
import com.processor.orderprocessing.domain.model.Order;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(UUID id);

    Optional<Order> findByIdWithHistory(UUID id);

    Page<Order> search(OrderSearchCriteria criteria);
}
