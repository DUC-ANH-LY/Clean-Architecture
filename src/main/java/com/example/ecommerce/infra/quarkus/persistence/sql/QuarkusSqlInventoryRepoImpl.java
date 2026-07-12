package com.example.ecommerce.infra.quarkus.persistence.sql;

import com.example.ecommerce.core.usecase.InventoryRepoI;
import com.example.ecommerce.infra.persistence.sql.entity.ProductJpaEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class QuarkusSqlInventoryRepoImpl implements InventoryRepoI {

    @Inject
    EntityManager em;

    @Override
    public boolean isStockAvailable(String productId, int quantity) {
        ProductJpaEntity product = em.find(ProductJpaEntity.class, productId);
        return product != null && product.getStockQuantity() >= quantity;
    }

    @Override
    @Transactional
    public void reduceStock(String productId, int quantity) {
        int updatedRows = em.createQuery(
                "UPDATE ProductJpaEntity p SET p.stockQuantity = p.stockQuantity - :qty " +
                "WHERE p.id = :id AND p.stockQuantity >= :qty")
                .setParameter("qty", quantity)
                .setParameter("id", productId)
                .executeUpdate();

        if (updatedRows == 0) {
            throw new IllegalStateException("Failed to reduce stock for product: " + productId);
        }
    }
}
