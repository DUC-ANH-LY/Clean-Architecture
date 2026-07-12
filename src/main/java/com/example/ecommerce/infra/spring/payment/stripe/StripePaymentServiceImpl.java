package com.example.ecommerce.infra.spring.payment.stripe;

import com.example.ecommerce.core.usecase.PaymentServiceI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Profile("stripe")
public class StripePaymentServiceImpl implements PaymentServiceI {

    @Value("${stripe.secret-key}")
    private String secretKey;

    @Override
    public String processPayment(String customerId, BigDecimal amount, String paymentToken) {
        System.out.printf("[Stripe] Charging customer=%s amount=%s token=%s%n",
                customerId, amount, paymentToken);
        return "stripe_txn_" + UUID.randomUUID();
    }
}
