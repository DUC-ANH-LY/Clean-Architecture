package com.example.ecommerce.config.spring;

import com.example.ecommerce.core.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
