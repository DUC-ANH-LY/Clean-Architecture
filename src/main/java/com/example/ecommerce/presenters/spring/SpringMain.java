package com.example.ecommerce.presenters.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "com.example.ecommerce.config.spring",
    "com.example.ecommerce.presenters.spring",
    "com.example.ecommerce.infra.persistence.sql.spring",
    "com.example.ecommerce.infra.persistence.nosql.spring",
    "com.example.ecommerce.infra.payment.stripe.spring",
    "com.example.ecommerce.infra.payment.vnpay.spring"
})
public class SpringMain {

    public static void main(String[] args) {
        SpringApplication.run(SpringMain.class, args);
    }
}
