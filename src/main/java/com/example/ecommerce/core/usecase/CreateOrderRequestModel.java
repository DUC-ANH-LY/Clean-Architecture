package com.example.ecommerce.core.usecase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequestModel {

    private String customerId;
    private List<OrderItemRequest> items;
    private String paymentToken;

    public record OrderItemRequest(String productId, int quantity) {}
}
