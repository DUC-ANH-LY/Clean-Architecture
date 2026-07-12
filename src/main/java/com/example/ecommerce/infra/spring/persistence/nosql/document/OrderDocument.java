package com.example.ecommerce.infra.spring.persistence.nosql.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDocument {

    @Id
    private String id;

    @Field("customer_id")
    private String customerId;

    @Field("total_amount")
    private BigDecimal totalAmount;

    private String status;

    @Field("created_at")
    private LocalDateTime createdAt;

    private List<OrderItemEmbed> items;

    public record OrderItemEmbed(String productId, int quantity, BigDecimal unitPrice) {}
}
