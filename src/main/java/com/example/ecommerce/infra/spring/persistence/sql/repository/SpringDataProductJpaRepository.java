package com.example.ecommerce.infra.spring.persistence.sql.repository;

import com.example.ecommerce.infra.persistence.sql.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SpringDataProductJpaRepository extends JpaRepository<ProductJpaEntity, String> {

    @Modifying
    @Transactional
    @Query("UPDATE ProductJpaEntity p SET p.stockQuantity = p.stockQuantity - :qty WHERE p.id = :id AND p.stockQuantity >= :qty")
    int reduceStock(@Param("id") String productId, @Param("qty") int quantity);
}
