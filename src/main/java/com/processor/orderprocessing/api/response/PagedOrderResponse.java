package com.processor.orderprocessing.api.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PagedOrderResponse {

    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private List<OrderSummaryResponse> orders;
}
