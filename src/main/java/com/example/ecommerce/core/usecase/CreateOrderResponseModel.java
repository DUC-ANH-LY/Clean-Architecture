package com.example.ecommerce.core.usecase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderResponseModel {
    private String orderId;
    private String status;
    private String message;
    private String paymentTransactionId;
}
