package com.example.ecommerce.core.usecase;

/**
 * Output model for the CreateOrderUseCase.
 * Pure Java — no framework annotations.
 */
public class CreateOrderResponseModel {

    private String orderId;
    private String status;
    private String message;
    private String paymentTransactionId;

    public CreateOrderResponseModel() {}

    public CreateOrderResponseModel(String orderId, String status, String message, String paymentTransactionId) {
        this.orderId = orderId;
        this.status = status;
        this.message = message;
        this.paymentTransactionId = paymentTransactionId;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getPaymentTransactionId() { return paymentTransactionId; }
    public void setPaymentTransactionId(String paymentTransactionId) { this.paymentTransactionId = paymentTransactionId; }
}
