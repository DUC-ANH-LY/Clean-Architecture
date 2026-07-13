package com.example.ecommerce.core.entity;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testGettersAndSetters() {
        Order order = new Order();
        LocalDateTime now = LocalDateTime.now();
        List<OrderItem> items = Collections.singletonList(new OrderItem("p-1", 1, BigDecimal.TEN));

        order.setId("order-1");
        order.setCustomerId("cust-1");
        order.setItems(items);
        order.setTotalAmount(BigDecimal.TEN);
        order.setStatus(Order.OrderStatus.PAID);
        order.setCreatedAt(now);

        assertEquals("order-1", order.getId());
        assertEquals("cust-1", order.getCustomerId());
        assertEquals(items, order.getItems());
        assertEquals(BigDecimal.TEN, order.getTotalAmount());
        assertEquals(Order.OrderStatus.PAID, order.getStatus());
        assertEquals(now, order.getCreatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        List<OrderItem> items1 = Collections.singletonList(new OrderItem("p-1", 1, BigDecimal.TEN));
        List<OrderItem> items2 = Collections.singletonList(new OrderItem("p-1", 1, BigDecimal.TEN));

        Order o1 = new Order("ord-1", "c-1", items1, BigDecimal.TEN, Order.OrderStatus.PAID, now);
        Order o2 = new Order("ord-1", "c-1", items2, BigDecimal.TEN, Order.OrderStatus.PAID, now);
        Order o3 = new Order("ord-2", "c-1", items1, BigDecimal.TEN, Order.OrderStatus.PAID, now);

        assertEquals(o1, o1);
        assertEquals(o1, o2);
        assertEquals(o1.hashCode(), o2.hashCode());

        assertNotEquals(o1, o3);
        assertNotEquals(o1, null);
        assertNotEquals(o1, new Object());

        assertTrue(o1.canEqual(o2));
        assertFalse(o1.canEqual(new Object()));
    }

    @Test
    void testEqualsAndHashCodeWithNulls() {
        Order o1 = new Order(null, null, null, null, null, null);
        Order o2 = new Order(null, null, null, null, null, null);
        Order o_id = new Order("ord-1", null, null, null, null, null);
        Order o_cust = new Order(null, "c-1", null, null, null, null);
        Order o_items = new Order(null, null, Collections.emptyList(), null, null, null);
        Order o_amount = new Order(null, null, null, BigDecimal.TEN, null, null);
        Order o_status = new Order(null, null, null, null, Order.OrderStatus.PAID, null);
        Order o_created = new Order(null, null, null, null, null, LocalDateTime.now());

        assertEquals(o1, o2);
        assertEquals(o1.hashCode(), o2.hashCode());

        assertNotEquals(o1, o_id);
        assertNotEquals(o_id, o1);
        assertNotEquals(o1, o_cust);
        assertNotEquals(o_cust, o1);
        assertNotEquals(o1, o_items);
        assertNotEquals(o_items, o1);
        assertNotEquals(o1, o_amount);
        assertNotEquals(o_amount, o1);
        assertNotEquals(o1, o_status);
        assertNotEquals(o_status, o1);
        assertNotEquals(o1, o_created);
        assertNotEquals(o_created, o1);

        Order subclass = new Order() {
            @Override
            public boolean canEqual(Object other) {
                return false;
            }
        };
        assertNotEquals(o1, subclass);
    }

    @Test
    void testToString() {
        LocalDateTime now = LocalDateTime.now();
        Order order = new Order("ord-1", "c-1", Collections.emptyList(), BigDecimal.ZERO, Order.OrderStatus.PENDING, now);
        String str = order.toString();
        assertNotNull(str);
        assertTrue(str.contains("id=ord-1"));
        assertTrue(str.contains("customerId=c-1"));
        assertTrue(str.contains("status=PENDING"));
    }

    @Test
    void testOrderStatusEnum() {
        Order.OrderStatus[] statuses = Order.OrderStatus.values();
        assertEquals(3, statuses.length);
        assertEquals(Order.OrderStatus.PENDING, Order.OrderStatus.valueOf("PENDING"));
        assertEquals(Order.OrderStatus.PAID, Order.OrderStatus.valueOf("PAID"));
        assertEquals(Order.OrderStatus.FAILED, Order.OrderStatus.valueOf("FAILED"));
    }
}
