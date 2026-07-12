package com.example.ecommerce.infra.payment.vnpay.spring;

import com.example.ecommerce.core.usecase.PaymentServiceI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * VNPay implementation of PaymentServiceI.
 * Active only when Spring profile "vnpay" is enabled.
 *
 * In production, use VNPay's API to generate payment URLs or
 * process direct charges via their SDK/HTTP integration.
 */
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
        // TODO: Replace with real VNPay HTTP call
        // 1. Build VNPay payment request params (vnp_TmnCode, vnp_Amount, vnp_SecureHash, etc.)
        // 2. Sign the request with hashSecret using HMAC-SHA512
        // 3. Send HTTP request to vnpayUrl
        // 4. Parse and return the transaction ID

        System.out.printf("[VNPay] Charging customer=%s amount=%s VND token=%s%n",
                customerId, amount, paymentToken);
        return "vnpay_txn_" + UUID.randomUUID();
    }
}
