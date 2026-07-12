package com.example.ecommerce.core.entity;

import java.math.BigDecimal;

/**
 * Core Domain Entity — pure Java, zero framework dependencies.
 */
public class Product {

    private String id;
    private String name;
    private BigDecimal price;
    private int stockQuantity;

    public Product() {}

    public Product(String id, String name, BigDecimal price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
}
