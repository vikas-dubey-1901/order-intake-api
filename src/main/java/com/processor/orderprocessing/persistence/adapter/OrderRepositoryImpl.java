package com.processor.orderprocessing.persistence.adapter;

import com.processor.orderprocessing.application.OrderSearchCriteria;
import com.processor.orderprocessing.application.port.OrderRepository;
import com.processor.orderprocessing.domain.model.Order;
import com.processor.orderprocessing.persistence.entity.OrderEntity;
import com.processor.orderprocessing.persistence.mapper.OrderPersistenceMapper;
import com.processor.orderprocessing.persistence.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository jpaRepository;
    private final OrderPersistenceMapper mapper;

    @Override
    public Order save(Order order) {

        OrderEntity entity = mapper.toEntity(order);

        OrderEntity saved = jpaRepository.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Order> findById(UUID id) {

        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Order> findByIdWithHistory(UUID id) {

        return jpaRepository.findByIdWithItems(id)
                .map(mapper::toDomain);
    }

    @Override
    public Page<Order> search(OrderSearchCriteria criteria) {

        // For now: simple placeholder
        Page<OrderEntity> page =
                jpaRepository.findAll(
                        criteria.toPageable()
                );

        return page.map(mapper::toDomain);
    }
}
