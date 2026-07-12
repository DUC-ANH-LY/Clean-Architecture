package com.example.ecommerce.infra.persistence.sql.spring.repository;

import com.example.ecommerce.infra.persistence.sql.entity.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataOrderJpaRepository extends JpaRepository<OrderJpaEntity, String> {
}
