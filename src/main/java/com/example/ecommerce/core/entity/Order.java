package com.example.ecommerce.core.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Core Domain Entity — pure Java, zero framework dependencies.
 */
public class Order {

    public enum OrderStatus { PENDING, PAID, FAILED }

    private String id;
    private String customerId;
    private List<OrderItem> items;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public Order() {}

    public Order(String id, String customerId, List<OrderItem> items,
                 BigDecimal totalAmount, OrderStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
