package com.example.ecommerce.infra.payment.stripe.spring;

import com.example.ecommerce.core.usecase.PaymentServiceI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Stripe implementation of PaymentServiceI.
 * Active only when Spring profile "stripe" is enabled.
 *
 * In production, use the Stripe Java SDK:
 *   Stripe.apiKey = secretKey;
 *   PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()...build();
 *   PaymentIntent intent = PaymentIntent.create(params);
 */
@Service
@Profile("stripe")
public class StripePaymentServiceImpl implements PaymentServiceI {

    @Value("${stripe.secret-key}")
    private String secretKey;

    @Override
    public String processPayment(String customerId, BigDecimal amount, String paymentToken) {
        // TODO: Replace with real Stripe SDK call
        // Stripe.apiKey = secretKey;
        // PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
        //     .setAmount(amount.multiply(BigDecimal.valueOf(100)).longValue()) // cents
        //     .setCurrency("usd")
        //     .setCustomer(customerId)
        //     .setPaymentMethod(paymentToken)
        //     .setConfirm(true)
        //     .build();
        // PaymentIntent intent = PaymentIntent.create(params);
        // return intent.getId();

        System.out.printf("[Stripe] Charging customer=%s amount=%s token=%s%n",
                customerId, amount, paymentToken);
        return "stripe_txn_" + UUID.randomUUID();
    }
}
