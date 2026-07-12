package com.example.ecommerce.infra.persistence.nosql;

import com.example.ecommerce.core.entity.Order;
import com.example.ecommerce.core.entity.OrderItem;
import com.example.ecommerce.core.usecase.OrderRepoI;
import com.example.ecommerce.infra.persistence.nosql.document.OrderDocument;
import com.example.ecommerce.infra.persistence.nosql.repository.SpringDataOrderMongoRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * MongoDB implementation of OrderRepoI.
 * Active only when Spring profile "nosql" is enabled.
 * Translates between core Order entity ↔ OrderDocument.
 */
@Repository
@Profile("nosql")
public class MongoOrderRepoImpl implements OrderRepoI {

    private final SpringDataOrderMongoRepository mongoRepository;

    public MongoOrderRepoImpl(SpringDataOrderMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public Order save(Order order) {
        OrderDocument document = toDocument(order);
        OrderDocument saved = mongoRepository.save(document);
        return toDomain(saved);
    }

    // ---- Mapping ----

    private OrderDocument toDocument(Order order) {
        OrderDocument doc = new OrderDocument();
        doc.setCustomerId(order.getCustomerId());
        doc.setTotalAmount(order.getTotalAmount());
        doc.setStatus(order.getStatus().name());
        doc.setCreatedAt(order.getCreatedAt());

        List<OrderDocument.OrderItemEmbed> embeds = order.getItems().stream()
                .map(i -> new OrderDocument.OrderItemEmbed(i.getProductId(), i.getQuantity(), i.getUnitPrice()))
                .collect(Collectors.toList());
        doc.setItems(embeds);
        return doc;
    }

    private Order toDomain(OrderDocument doc) {
        List<OrderItem> items = doc.getItems().stream()
                .map(e -> new OrderItem(e.productId(), e.quantity(), e.unitPrice()))
                .collect(Collectors.toList());

        return new Order(
                doc.getId(),
                doc.getCustomerId(),
                items,
                doc.getTotalAmount(),
                Order.OrderStatus.valueOf(doc.getStatus()),
                doc.getCreatedAt()
        );
    }
}
