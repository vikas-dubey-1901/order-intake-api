package com.processor.orderprocessing.application.view;

import com.processor.orderprocessing.domain.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
public class PagedOrderView {

    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private List<OrderSummaryView> orders;

    public static PagedOrderView from(Page<Order> page) {

        List<OrderSummaryView> content =
                page.getContent()
                        .stream()
                        .map(OrderSummaryView::from)
                        .toList();

        return new PagedOrderView(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                content
        );
    }
}
