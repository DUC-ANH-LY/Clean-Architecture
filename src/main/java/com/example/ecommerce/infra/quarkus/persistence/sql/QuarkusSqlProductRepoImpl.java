package com.example.ecommerce.infra.quarkus.persistence.sql;

import com.example.ecommerce.core.entity.Product;
import com.example.ecommerce.core.usecase.ProductRepoI;
import com.example.ecommerce.infra.persistence.sql.entity.ProductJpaEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.Optional;

@ApplicationScoped
public class QuarkusSqlProductRepoImpl implements ProductRepoI {

    @Inject
    EntityManager em;

    @Override
    public Optional<Product> findById(String productId) {
        ProductJpaEntity entity = em.find(ProductJpaEntity.class, productId);
        if (entity == null) {
            return Optional.empty();
        }
        return Optional.of(new Product(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getStockQuantity()
        ));
    }
}
