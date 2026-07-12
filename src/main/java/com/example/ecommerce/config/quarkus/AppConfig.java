package com.example.ecommerce.config.quarkus;

import com.example.ecommerce.core.usecase.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class AppConfig {

    @Inject
    ProductRepoI productRepo;

    @Inject
    InventoryRepoI inventoryRepo;

    @Inject
    OrderRepoI orderRepo;

    @Inject
    @Named("stripe")
    PaymentServiceI stripePaymentService;

    @Inject
    @Named("vnpay")
    PaymentServiceI vnpayPaymentService;

    @ConfigProperty(name = "payment.provider", defaultValue = "stripe")
    String paymentProvider;

    @Produces
    @ApplicationScoped
    public CreateOrderUseCase createOrderUseCase() {
        PaymentServiceI paymentService = "vnpay".equalsIgnoreCase(paymentProvider) 
                ? vnpayPaymentService 
                : stripePaymentService;

        return new CreateOrderUseCase(productRepo, inventoryRepo, paymentService, orderRepo);
    }
}
