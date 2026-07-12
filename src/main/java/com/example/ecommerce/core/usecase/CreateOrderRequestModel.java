package com.example.ecommerce.core.usecase;

import java.util.List;

/**
 * Input model for the CreateOrderUseCase.
 * Pure Java — no framework annotations.
 */
public class CreateOrderRequestModel {

    private String customerId;
    private List<OrderItemRequest> items;
    private String paymentToken;

    public CreateOrderRequestModel() {}

    public CreateOrderRequestModel(String customerId, List<OrderItemRequest> items, String paymentToken) {
        this.customerId = customerId;
        this.items = items;
        this.paymentToken = paymentToken;
    }

    public record OrderItemRequest(String productId, int quantity) {}

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
    public String getPaymentToken() { return paymentToken; }
    public void setPaymentToken(String paymentToken) { this.paymentToken = paymentToken; }
}
