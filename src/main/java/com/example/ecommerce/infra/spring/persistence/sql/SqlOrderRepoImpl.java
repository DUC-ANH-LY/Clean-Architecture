package com.example.ecommerce.infra.spring.persistence.sql;

import com.example.ecommerce.core.entity.Order;
import com.example.ecommerce.core.entity.OrderItem;
import com.example.ecommerce.core.usecase.OrderRepoI;
import com.example.ecommerce.infra.persistence.sql.entity.OrderItemJpaEntity;
import com.example.ecommerce.infra.persistence.sql.entity.OrderJpaEntity;
import com.example.ecommerce.infra.spring.persistence.sql.repository.SpringDataOrderJpaRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Profile("sql")
public class SqlOrderRepoImpl implements OrderRepoI {

    private final SpringDataOrderJpaRepository jpaRepository;

    public SqlOrderRepoImpl(SpringDataOrderJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Order save(Order order) {
        OrderJpaEntity entity = toJpaEntity(order);
        OrderJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    private OrderJpaEntity toJpaEntity(Order order) {
        OrderJpaEntity entity = new OrderJpaEntity();
        entity.setCustomerId(order.getCustomerId());
        entity.setTotalAmount(order.getTotalAmount());
        entity.setStatus(order.getStatus().name());
        entity.setCreatedAt(order.getCreatedAt());

        List<OrderItemJpaEntity> itemEntities = order.getItems().stream().map(item -> {
            OrderItemJpaEntity ie = new OrderItemJpaEntity();
            ie.setProductId(item.getProductId());
            ie.setQuantity(item.getQuantity());
            ie.setUnitPrice(item.getUnitPrice());
            ie.setOrder(entity);
            return ie;
        }).collect(Collectors.toList());

        entity.setItems(itemEntities);
        return entity;
    }

    private Order toDomain(OrderJpaEntity entity) {
        List<OrderItem> items = entity.getItems().stream()
                .map(ie -> new OrderItem(ie.getProductId(), ie.getQuantity(), ie.getUnitPrice()))
                .collect(Collectors.toList());

        return new Order(
                entity.getId(),
                entity.getCustomerId(),
                items,
                entity.getTotalAmount(),
                Order.OrderStatus.valueOf(entity.getStatus()),
                entity.getCreatedAt()
        );
    }
}
