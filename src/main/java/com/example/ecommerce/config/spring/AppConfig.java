package com.example.ecommerce.config.spring;

import com.example.ecommerce.core.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * COMPOSITION ROOT — The only class that knows about all layers.
 * Wires the core CreateOrderUseCase with the active infra implementations.
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
