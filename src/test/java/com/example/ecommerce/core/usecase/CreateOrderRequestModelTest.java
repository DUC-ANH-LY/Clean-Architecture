package com.example.ecommerce.core.usecase;

import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CreateOrderRequestModelTest {

    @Test
    void testGettersAndSetters() {
        CreateOrderRequestModel model = new CreateOrderRequestModel();
        List<CreateOrderRequestModel.OrderItemRequest> items = Collections.singletonList(
                new CreateOrderRequestModel.OrderItemRequest("prod-1", 2)
        );

        model.setCustomerId("c-100");
        model.setItems(items);
        model.setPaymentToken("tok-stripe");

        assertEquals("c-100", model.getCustomerId());
        assertEquals(items, model.getItems());
        assertEquals("tok-stripe", model.getPaymentToken());
    }

    @Test
    void testEqualsAndHashCode() {
        List<CreateOrderRequestModel.OrderItemRequest> items1 = Collections.singletonList(
                new CreateOrderRequestModel.OrderItemRequest("prod-1", 2)
        );
        List<CreateOrderRequestModel.OrderItemRequest> items2 = Collections.singletonList(
                new CreateOrderRequestModel.OrderItemRequest("prod-1", 2)
        );

        CreateOrderRequestModel m1 = new CreateOrderRequestModel("c-100", items1, "tok-stripe");
        CreateOrderRequestModel m2 = new CreateOrderRequestModel("c-100", items2, "tok-stripe");
        CreateOrderRequestModel m3 = new CreateOrderRequestModel("c-200", items1, "tok-stripe");

        assertEquals(m1, m1);
        assertEquals(m1, m2);
        assertEquals(m1.hashCode(), m2.hashCode());

        assertNotEquals(m1, m3);
        assertNotEquals(m1, null);
        assertNotEquals(m1, new Object());

        assertTrue(m1.canEqual(m2));
        assertFalse(m1.canEqual(new Object()));
    }

    @Test
    void testEqualsAndHashCodeWithNulls() {
        CreateOrderRequestModel m1 = new CreateOrderRequestModel(null, null, null);
        CreateOrderRequestModel m2 = new CreateOrderRequestModel(null, null, null);
        CreateOrderRequestModel m_cust = new CreateOrderRequestModel("c-100", null, null);
        CreateOrderRequestModel m_items = new CreateOrderRequestModel(null, Collections.emptyList(), null);
        CreateOrderRequestModel m_tok = new CreateOrderRequestModel(null, null, "tok-stripe");

        assertEquals(m1, m2);
        assertEquals(m1.hashCode(), m2.hashCode());

        assertNotEquals(m1, m_cust);
        assertNotEquals(m_cust, m1);
        assertNotEquals(m1, m_items);
        assertNotEquals(m_items, m1);
        assertNotEquals(m1, m_tok);
        assertNotEquals(m_tok, m1);

        CreateOrderRequestModel subclass = new CreateOrderRequestModel() {
            @Override
            public boolean canEqual(Object other) {
                return false;
            }
        };
        assertNotEquals(m1, subclass);
    }

    @Test
    void testToString() {
        CreateOrderRequestModel model = new CreateOrderRequestModel("c-100", Collections.emptyList(), "tok-stripe");
        String str = model.toString();
        assertNotNull(str);
        assertTrue(str.contains("customerId=c-100"));
        assertTrue(str.contains("paymentToken=tok-stripe"));
    }

    @Test
    void testOrderItemRequestRecord() {
        CreateOrderRequestModel.OrderItemRequest r1 = new CreateOrderRequestModel.OrderItemRequest("prod-1", 5);
        CreateOrderRequestModel.OrderItemRequest r2 = new CreateOrderRequestModel.OrderItemRequest("prod-1", 5);
        CreateOrderRequestModel.OrderItemRequest r3 = new CreateOrderRequestModel.OrderItemRequest("prod-2", 5);

        assertEquals("prod-1", r1.productId());
        assertEquals(5, r1.quantity());

        assertEquals(r1, r1);
        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
        assertNotEquals(r1, r3);
        assertNotEquals(r1, null);

        String str = r1.toString();
        assertNotNull(str);
        assertTrue(str.contains("productId=prod-1"));
        assertTrue(str.contains("quantity=5"));
    }
}
