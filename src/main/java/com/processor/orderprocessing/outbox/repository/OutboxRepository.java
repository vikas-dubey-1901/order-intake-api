package com.processor.orderprocessing.outbox.repository;

import com.processor.orderprocessing.outbox.entity.OutboxEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxRepository extends JpaRepository<OutboxEventEntity , Long> {

    List<OutboxEventEntity> findTop100ByStatusOrderByCreatedAtAsc(String status);

}
