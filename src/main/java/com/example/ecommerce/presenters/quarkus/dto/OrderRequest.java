package com.example.ecommerce.presenters.quarkus.dto;

import com.example.ecommerce.core.usecase.CreateOrderRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
