package com.processor.orderprocessing.persistence.entity;

import com.processor.orderprocessing.domain.domainEnum.OrderChannel;
import com.processor.orderprocessing.domain.domainEnum.OrderStatus;
import com.processor.orderprocessing.domain.domainEnum.OrderType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderEntity {

    @Id
    @Column(name = "order_id", nullable = false , updatable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderType orderType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderChannel channel;

    @Column(nullable = false)
    private String customerId;

    @Enumerated(EnumType.STRING)
    @Column
    private OrderStatus status;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<OrderItemEntity> items = new ArrayList<>();

    static OrderEntity fromDomain(
            UUID id,
            OrderType orderType,
            OrderChannel channel,
            String customerId,
            OrderStatus status,
            String currency,
            Instant createdAt,
            Instant updatedAt
    ) {
        OrderEntity entity = new OrderEntity();
        entity.id = id;
        entity.orderType = orderType;
        entity.channel = channel;
        entity.customerId = customerId;
        entity.status = status;
        entity.currency = currency;
        entity.createdAt = createdAt;
        entity.updatedAt = updatedAt;
        return entity;
    }

    void addItem(OrderItemEntity item) {
        items.add(item);
        item.setOrder(this);
    }

    void clearItems() {
        items.clear();
    }

}
