package com.example.ecommerce.infra.spring.persistence.sql;

import com.example.ecommerce.core.usecase.InventoryRepoI;
import com.example.ecommerce.infra.spring.persistence.sql.repository.SpringDataProductJpaRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("sql")
public class SqlInventoryRepoImpl implements InventoryRepoI {

    private final SpringDataProductJpaRepository jpaRepository;

    public SqlInventoryRepoImpl(SpringDataProductJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public boolean isStockAvailable(String productId, int quantity) {
        return jpaRepository.findById(productId)
                .map(p -> p.getStockQuantity() >= quantity)
                .orElse(false);
    }

    @Override
    public void reduceStock(String productId, int quantity) {
        int updated = jpaRepository.reduceStock(productId, quantity);
        if (updated == 0) {
            throw new IllegalStateException("Failed to reduce stock for product: " + productId);
        }
    }
}
