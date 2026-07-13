package com.example.ecommerce.core.entity;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testGettersAndSetters() {
        Product product = new Product();
        product.setId("prod-111");
        product.setName("Test Product");
        product.setPrice(new BigDecimal("99.99"));
        product.setStockQuantity(50);

        assertEquals("prod-111", product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals(new BigDecimal("99.99"), product.getPrice());
        assertEquals(50, product.getStockQuantity());
    }

    @Test
    void testEqualsAndHashCode() {
        Product p1 = new Product("prod-111", "Laptop", new BigDecimal("999.99"), 10);
        Product p2 = new Product("prod-111", "Laptop", new BigDecimal("999.99"), 10);
        Product p3 = new Product("prod-222", "Laptop", new BigDecimal("999.99"), 10);
        Product p4 = new Product("prod-111", "Desktop", new BigDecimal("999.99"), 10);
        Product p5 = new Product("prod-111", "Laptop", new BigDecimal("899.99"), 10);
        Product p6 = new Product("prod-111", "Laptop", new BigDecimal("999.99"), 5);

        assertEquals(p1, p1);
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());

        assertNotEquals(p1, p3);
        assertNotEquals(p1, p4);
        assertNotEquals(p1, p5);
        assertNotEquals(p1, p6);
        assertNotEquals(p1, null);
        assertNotEquals(p1, new Object());

        assertTrue(p1.canEqual(p2));
        assertFalse(p1.canEqual(new Object()));
    }

    @Test
    void testEqualsAndHashCodeWithNulls() {
        Product p1 = new Product(null, null, null, 0);
        Product p2 = new Product(null, null, null, 0);
        Product p_id = new Product("prod-111", null, null, 0);
        Product p_name = new Product(null, "Laptop", null, 0);
        Product p_price = new Product(null, null, new BigDecimal("999.99"), 0);

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());

        assertNotEquals(p1, p_id);
        assertNotEquals(p_id, p1);
        assertNotEquals(p1, p_name);
        assertNotEquals(p_name, p1);
        assertNotEquals(p1, p_price);
        assertNotEquals(p_price, p1);

        Product subclass = new Product() {
            @Override
            public boolean canEqual(Object other) {
                return false;
            }
        };
        assertNotEquals(p1, subclass);
    }

    @Test
    void testToString() {
        Product product = new Product("prod-111", "Laptop", new BigDecimal("999.99"), 10);
        String str = product.toString();
        assertNotNull(str);
        assertTrue(str.contains("id=prod-111"));
        assertTrue(str.contains("name=Laptop"));
        assertTrue(str.contains("price=999.99"));
        assertTrue(str.contains("stockQuantity=10"));
    }
}
