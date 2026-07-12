package com.example.ecommerce.core.usecase;

import com.example.ecommerce.core.entity.Order;

/**
 * Outbound interface — defined in core, implemented in infra.
 * Decouples the use case from any specific persistence technology.
 */
public interface OrderRepoI {
    Order save(Order order);
}
