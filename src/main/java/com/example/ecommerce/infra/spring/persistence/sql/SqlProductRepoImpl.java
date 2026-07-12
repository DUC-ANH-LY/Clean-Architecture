package com.example.ecommerce.infra.spring.persistence.sql;

import com.example.ecommerce.core.entity.Product;
import com.example.ecommerce.core.usecase.ProductRepoI;
import com.example.ecommerce.infra.persistence.sql.entity.ProductJpaEntity;
import com.example.ecommerce.infra.spring.persistence.sql.repository.SpringDataProductJpaRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("sql")
public class SqlProductRepoImpl implements ProductRepoI {

    private final SpringDataProductJpaRepository jpaRepository;

    public SqlProductRepoImpl(SpringDataProductJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Product> findById(String productId) {
        return jpaRepository.findById(productId).map(this::toDomain);
    }

    private Product toDomain(ProductJpaEntity entity) {
        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getStockQuantity()
        );
    }
}
