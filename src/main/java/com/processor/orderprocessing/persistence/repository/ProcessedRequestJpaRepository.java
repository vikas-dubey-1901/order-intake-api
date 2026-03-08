package com.processor.orderprocessing.persistence.repository;

import com.processor.orderprocessing.persistence.entity.ProcessedRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedRequestJpaRepository
        extends JpaRepository<ProcessedRequestEntity, String> {
}