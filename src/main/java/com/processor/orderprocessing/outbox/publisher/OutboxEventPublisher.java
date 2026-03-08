package com.processor.orderprocessing.outbox.publisher;

import com.processor.orderprocessing.outbox.entity.OutboxEventEntity;
import com.processor.orderprocessing.outbox.repository.OutboxJpaRepository;
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

    private final OutboxJpaRepository repository;
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
                String topic = event.getEventType()
                        .toLowerCase()
                        .replace("_", "-");

                kafkaTemplate.send(
                        topic,
                        event.getAggregateId(),
                        event.getPayload()
                ).get();  // WAIT FOR ACK

                event.markPublished();

                log.info(
                        "Published event {} for aggregate {}",
                        event.getEventType(),
                        event.getAggregateId()
                );

            } catch (Exception ex) {
                log.error("Failed to publish event {}", event.getId(), ex);
                throw new RuntimeException(ex);
            }
        }
    }
}
