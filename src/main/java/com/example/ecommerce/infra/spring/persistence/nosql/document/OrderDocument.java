package com.example.ecommerce.infra.spring.persistence.nosql.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "orders")
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

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<OrderItemEmbed> getItems() { return items; }
    public void setItems(List<OrderItemEmbed> items) { this.items = items; }
}
