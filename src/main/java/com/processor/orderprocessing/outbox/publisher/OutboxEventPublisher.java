package com.processor.orderprocessing.outbox.publisher;

import com.processor.orderprocessing.outbox.entity.OutboxEventEntity;
import com.processor.orderprocessing.outbox.repository.OutboxRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxEventPublisher {

    private final OutboxRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Transactional
    @Scheduled(fixedDelay = 5000)
    public void publishPendingEvents() {
        List<OutboxEventEntity> events =
                repository.findTop100ByStatusOrderByCreatedAtAsc("PENDING");

        if (events.isEmpty()) {
            return;
        }

        for (OutboxEventEntity event : events) {
            try {

                kafkaTemplate.send(
                        event.getEventType(),
                        event.getAggregateId(),
                        event.getPayload()
                );

                event.markPublished();

            } catch (Exception ex) {
                log.error("Failed to publish event {}", event.getId(), ex);
                // Let transaction roll back so event remains PENDING
                throw ex;
            }
        }
    }

}
