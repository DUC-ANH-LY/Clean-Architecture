package com.example.ecommerce.infra.persistence.sql.quarkus;

import com.example.ecommerce.core.entity.Order;
import com.example.ecommerce.core.entity.OrderItem;
import com.example.ecommerce.core.usecase.OrderRepoI;
import com.example.ecommerce.infra.persistence.sql.entity.OrderItemJpaEntity;
import com.example.ecommerce.infra.persistence.sql.entity.OrderJpaEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class QuarkusSqlOrderRepoImpl implements OrderRepoI {

    @Inject
    EntityManager em;

    @Override
    @Transactional
    public Order save(Order order) {
        OrderJpaEntity entity = toJpaEntity(order);
        if (entity.getId() == null) {
            em.persist(entity);
        } else {
            entity = em.merge(entity);
        }
        return toDomain(entity);
    }

    private OrderJpaEntity toJpaEntity(Order order) {
        OrderJpaEntity entity = new OrderJpaEntity();
        entity.setId(order.getId());
        entity.setCustomerId(order.getCustomerId());
        entity.setTotalAmount(order.getTotalAmount());
        entity.setStatus(order.getStatus().name());
        entity.setCreatedAt(order.getCreatedAt());

        if (order.getItems() != null) {
            List<OrderItemJpaEntity> items = order.getItems().stream().map(item -> {
                OrderItemJpaEntity ie = new OrderItemJpaEntity();
                ie.setProductId(item.getProductId());
                ie.setQuantity(item.getQuantity());
                ie.setUnitPrice(item.getUnitPrice());
                ie.setOrder(entity);
                return ie;
            }).collect(Collectors.toList());
            entity.setItems(items);
        }
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
