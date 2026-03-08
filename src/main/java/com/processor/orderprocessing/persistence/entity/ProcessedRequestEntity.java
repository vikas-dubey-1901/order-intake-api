package com.processor.orderprocessing.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "processed_requests")
@Getter
@NoArgsConstructor
public class ProcessedRequestEntity {

    @Id
    private String requestId;

    private Instant processedAt;

    public ProcessedRequestEntity(String requestId) {
        this.requestId = requestId;
        this.processedAt = Instant.now();
    }
}