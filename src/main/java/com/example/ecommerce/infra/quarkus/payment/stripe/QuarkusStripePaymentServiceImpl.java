package com.example.ecommerce.infra.quarkus.payment.stripe;

import com.example.ecommerce.core.usecase.PaymentServiceI;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.math.BigDecimal;
import java.util.UUID;

@ApplicationScoped
@Named("stripe")
public class QuarkusStripePaymentServiceImpl implements PaymentServiceI {

    @ConfigProperty(name = "stripe.secret-key", defaultValue = "sk_test_placeholder")
    String secretKey;

    @Override
    public String processPayment(String customerId, BigDecimal amount, String paymentToken) {
        System.out.printf("[Quarkus Stripe] Charging customer=%s amount=%s token=%s%n",
                customerId, amount, paymentToken);
        return "stripe_txn_" + UUID.randomUUID();
    }
}
