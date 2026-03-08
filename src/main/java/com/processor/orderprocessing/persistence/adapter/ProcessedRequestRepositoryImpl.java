package com.processor.orderprocessing.persistence.adapter;

import com.processor.orderprocessing.application.port.ProcessedRequestRepository;
import com.processor.orderprocessing.persistence.entity.ProcessedRequestEntity;
import com.processor.orderprocessing.persistence.repository.ProcessedRequestJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessedRequestRepositoryImpl
        implements ProcessedRequestRepository {

    private final ProcessedRequestJpaRepository jpaRepository;

    @Override
    public boolean exists(String requestId) {
        return jpaRepository.existsById(requestId);
    }

    @Override
    public void save(String requestId) {
        jpaRepository.save(new ProcessedRequestEntity(requestId));
    }
}