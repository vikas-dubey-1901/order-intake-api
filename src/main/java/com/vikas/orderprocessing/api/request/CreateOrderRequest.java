package com.vikas.orderprocessing.api.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {

    @NotNull
    private OrderType orderType;

    @NotNull
    private OrderChannel channel;

    @NotBlank
    private String customerId;

    @NotEmpty
    @Valid
    private List<OrderItemRequest> items;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}$" , message = "Currency must be ISO-4217 format")
    private String currency;

    private Map<String, String> metadata;
}
