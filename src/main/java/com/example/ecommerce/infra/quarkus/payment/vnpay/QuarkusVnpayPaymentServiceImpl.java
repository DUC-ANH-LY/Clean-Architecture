package com.example.ecommerce.infra.quarkus.payment.vnpay;

import com.example.ecommerce.core.usecase.PaymentServiceI;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.math.BigDecimal;
import java.util.UUID;

@ApplicationScoped
@Named("vnpay")
public class QuarkusVnpayPaymentServiceImpl implements PaymentServiceI {

    @ConfigProperty(name = "vnpay.tmn-code", defaultValue = "PLACEHOLDER")
    String tmnCode;

    @ConfigProperty(name = "vnpay.hash-secret", defaultValue = "PLACEHOLDER")
    String hashSecret;

    @ConfigProperty(name = "vnpay.url", defaultValue = "PLACEHOLDER")
    String vnpayUrl;

    @Override
    public String processPayment(String customerId, BigDecimal amount, String paymentToken) {
        System.out.printf("[Quarkus VNPay] Charging customer=%s amount=%s VND token=%s%n",
                customerId, amount, paymentToken);
        return "vnpay_txn_" + UUID.randomUUID();
    }
}
