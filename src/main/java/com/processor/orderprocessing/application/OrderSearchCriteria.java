package com.processor.orderprocessing.application;

import com.processor.orderprocessing.domain.domainEnum.OrderChannel;
import com.processor.orderprocessing.domain.domainEnum.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.*;

import java.time.Instant;

@Getter
@Builder
public class OrderSearchCriteria {

    private String customerId;
    private OrderStatus status;
    private OrderChannel channel;
    private Instant fromDate;
    private Instant toDate;

    private int page;
    private int size;
    private String sort;

    public Pageable toPageable() {

        Sort sortObj = parseSort(sort);

        return PageRequest.of(
                page,
                size,
                sortObj
        );
    }

    private Sort parseSort(String sortParam) {

        if (sortParam == null || sortParam.isBlank()) {
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }

        String[] parts = sortParam.split(",");

        String field = parts[0];
        Sort.Direction direction =
                parts.length > 1 && parts[1].equalsIgnoreCase("asc")
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC;

        return Sort.by(direction, field);
    }
}
