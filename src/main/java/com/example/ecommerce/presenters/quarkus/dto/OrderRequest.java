package com.example.ecommerce.presenters.quarkus.dto;

import com.example.ecommerce.core.usecase.CreateOrderRequestModel;

import java.util.List;
import java.util.stream.Collectors;

/**
 * HTTP Request DTO for the Quarkus-style JAX-RS resource.
 * Converts JSON body → core CreateOrderRequestModel.
 */
public class OrderRequest {

    private String customerId;
    private List<OrderItemRequest> items;
    private String paymentToken;

    public record OrderItemRequest(String productId, int quantity) {}

    public CreateOrderRequestModel toRequestModel() {
        List<CreateOrderRequestModel.OrderItemRequest> modelItems = items.stream()
                .map(i -> new CreateOrderRequestModel.OrderItemRequest(i.productId(), i.quantity()))
                .collect(Collectors.toList());
        return new CreateOrderRequestModel(customerId, modelItems, paymentToken);
    }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
    public String getPaymentToken() { return paymentToken; }
    public void setPaymentToken(String paymentToken) { this.paymentToken = paymentToken; }
}
