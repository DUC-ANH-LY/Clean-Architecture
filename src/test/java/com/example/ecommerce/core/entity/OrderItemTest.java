package com.example.ecommerce.core.entity;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    @Test
    void testSubtotalCalculation() {
        OrderItem item = new OrderItem("prod-123", 3, new BigDecimal("19.99"));
        assertEquals(new BigDecimal("59.97"), item.subtotal());
    }

    @Test
    void testGettersAndSetters() {
        OrderItem item = new OrderItem();
        item.setProductId("prod-456");
        item.setQuantity(5);
        item.setUnitPrice(new BigDecimal("10.00"));

        assertEquals("prod-456", item.getProductId());
        assertEquals(5, item.getQuantity());
        assertEquals(new BigDecimal("10.00"), item.getUnitPrice());
    }

    @Test
    void testEqualsAndHashCode() {
        OrderItem item1 = new OrderItem("prod-123", 2, new BigDecimal("15.50"));
        OrderItem item2 = new OrderItem("prod-123", 2, new BigDecimal("15.50"));
        OrderItem item3 = new OrderItem("prod-999", 2, new BigDecimal("15.50"));
        OrderItem item4 = new OrderItem("prod-123", 3, new BigDecimal("15.50"));
        OrderItem item5 = new OrderItem("prod-123", 2, new BigDecimal("20.00"));

        // Reflexive
        assertEquals(item1, item1);

        // Symmetric & Equal
        assertEquals(item1, item2);
        assertEquals(item1.hashCode(), item2.hashCode());

        // Not Equal
        assertNotEquals(item1, item3);
        assertNotEquals(item1, item4);
        assertNotEquals(item1, item5);
        assertNotEquals(item1, null);
        assertNotEquals(item1, "some string");

        // CanEqual (Lombok specific)
        assertTrue(item1.canEqual(item2));
        assertFalse(item1.canEqual("some string"));
    }

    @Test
    void testEqualsAndHashCodeWithNulls() {
        OrderItem item1 = new OrderItem(null, 0, null);
        OrderItem item2 = new OrderItem(null, 0, null);
        OrderItem item3 = new OrderItem("prod-123", 0, null);
        OrderItem item4 = new OrderItem(null, 0, new BigDecimal("15.50"));

        assertEquals(item1, item2);
        assertEquals(item1.hashCode(), item2.hashCode());

        assertNotEquals(item1, item3);
        assertNotEquals(item3, item1);
        assertNotEquals(item1, item4);
        assertNotEquals(item4, item1);

        OrderItem subclass = new OrderItem() {
            @Override
            public boolean canEqual(Object other) {
                return false;
            }
        };
        assertNotEquals(item1, subclass);
    }

    @Test
    void testToString() {
        OrderItem item = new OrderItem("prod-123", 2, new BigDecimal("15.50"));
        String str = item.toString();
        assertNotNull(str);
        assertTrue(str.contains("productId=prod-123"));
        assertTrue(str.contains("quantity=2"));
        assertTrue(str.contains("unitPrice=15.50"));
    }
}
