package com.example.ecommerce.config;

import com.example.ecommerce.core.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * COMPOSITION ROOT — The only class that knows about all layers.
 *
 * Wires the core CreateOrderUseCase with the active infra implementations.
 * Spring automatically selects implementations based on @Profile:
 *  - sql profile    → SqlOrderRepoImpl, SqlProductRepoImpl, SqlInventoryRepoImpl
 *  - nosql profile  → MongoOrderRepoImpl, MongoProductRepoImpl, MongoInventoryRepoImpl
 *  - stripe profile → StripePaymentServiceImpl
 *  - vnpay profile  → VnpayPaymentServiceImpl
 *
 * The wired CreateOrderUseCase is injected into BOTH presenters:
 *  - Spring MVC  → OrderController  (/api/spring/orders)
 *  - JAX-RS      → OrderResource    (/api/quarkus/orders)
 */
@Configuration
public class AppConfig {

    @Bean
    public CreateOrderUseCase createOrderUseCase(
            ProductRepoI productRepo,
            InventoryRepoI inventoryRepo,
            PaymentServiceI paymentService,
            OrderRepoI orderRepo) {

        return new CreateOrderUseCase(productRepo, inventoryRepo, paymentService, orderRepo);
    }
}
