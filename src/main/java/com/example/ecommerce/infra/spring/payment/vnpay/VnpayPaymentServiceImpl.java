package com.example.ecommerce.infra.spring.payment.vnpay;

import com.example.ecommerce.core.usecase.PaymentServiceI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Profile("vnpay")
public class VnpayPaymentServiceImpl implements PaymentServiceI {

    @Value("${vnpay.tmn-code}")
    private String tmnCode;

    @Value("${vnpay.hash-secret}")
    private String hashSecret;

    @Value("${vnpay.url}")
    private String vnpayUrl;

    @Override
    public String processPayment(String customerId, BigDecimal amount, String paymentToken) {
        System.out.printf("[VNPay] Charging customer=%s amount=%s VND token=%s%n",
                customerId, amount, paymentToken);
        return "vnpay_txn_" + UUID.randomUUID();
    }
}
