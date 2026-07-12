package com.example.ecommerce.core.usecase;

import com.example.ecommerce.core.entity.Product;
import java.util.Optional;

/**
 * Outbound interface — defined in core, implemented in infra.
 * Decouples the use case from any specific database technology.
 */
public interface ProductRepoI {
    Optional<Product> findById(String productId);
}
