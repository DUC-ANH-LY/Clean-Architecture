package com.example.ecommerce.core.usecase;

/**
 * Outbound interface — defined in core, implemented in infra.
 * Decouples the use case from any specific inventory storage.
 */
public interface InventoryRepoI {
    boolean isStockAvailable(String productId, int quantity);
    void reduceStock(String productId, int quantity);
}
