package com.processor.orderprocessing.outbox.adapter;

import com.processor.orderprocessing.application.event.OutboxEvent;
import com.processor.orderprocessing.application.port.OutboxRepository;
import com.processor.orderprocessing.outbox.entity.OutboxEventEntity;
import com.processor.orderprocessing.outbox.repository.OutboxJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxRepositoryImpl implements OutboxRepository {

    private final OutboxJpaRepository jpaRepository;

    @Override
    public void save(OutboxEvent event) {

        OutboxEventEntity entity = OutboxEventEntity.create(
                event.getAggregateType(),
                event.getAggregateId(),
                event.getEventType(),
                event.getPayload()
        );

        jpaRepository.save(entity);
    }
}