package com.example.ecommerce.core.usecase;

import java.math.BigDecimal;

/**
 * Outbound interface — defined in core, implemented in infra.
 * The use case doesn't care whether Stripe or VNPay processes the payment.
 */
public interface PaymentServiceI {
    /**
     * @param customerId   The customer's identifier
     * @param amount       Total amount to charge
     * @param paymentToken Token from the frontend (e.g., Stripe card token)
     * @return             External payment transaction ID
     */
    String processPayment(String customerId, BigDecimal amount, String paymentToken);
}
