package com.processor.orderprocessing.persistence.repository;

import com.processor.orderprocessing.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {

    @Query("""
           select o from OrderEntity o
           left join fetch o.items
           where o.id = :orderId
           """)
    Optional<OrderEntity> findByIdWithItems(UUID orderId);
}
