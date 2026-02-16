package com.processor.orderprocessing.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal unitPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    public static OrderItemEntity fromDomain(
            String productId,
            int quantity,
            BigDecimal unitPrice
    ) {
        OrderItemEntity entity = new OrderItemEntity();
        entity.productId = productId;
        entity.quantity = quantity;
        entity.unitPrice = unitPrice;
        return entity;
    }

    void setOrder(OrderEntity order) {
        this.order = order;
    }
}
