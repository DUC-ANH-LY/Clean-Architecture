package com.example.ecommerce.core.usecase;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreateOrderResponseModelTest {

    @Test
    void testGettersAndSetters() {
        CreateOrderResponseModel model = new CreateOrderResponseModel();
        model.setOrderId("ord-123");
        model.setStatus("PAID");
        model.setMessage("Success");
        model.setPaymentTransactionId("txn-999");

        assertEquals("ord-123", model.getOrderId());
        assertEquals("PAID", model.getStatus());
        assertEquals("Success", model.getMessage());
        assertEquals("txn-999", model.getPaymentTransactionId());
    }

    @Test
    void testEqualsAndHashCode() {
        CreateOrderResponseModel m1 = new CreateOrderResponseModel("ord-123", "PAID", "Success", "txn-999");
        CreateOrderResponseModel m2 = new CreateOrderResponseModel("ord-123", "PAID", "Success", "txn-999");
        CreateOrderResponseModel m3 = new CreateOrderResponseModel("ord-123", "FAILED", "Success", "txn-999");

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
        CreateOrderResponseModel r1 = new CreateOrderResponseModel(null, null, null, null);
        CreateOrderResponseModel r2 = new CreateOrderResponseModel(null, null, null, null);
        CreateOrderResponseModel r_id = new CreateOrderResponseModel("ord-123", null, null, null);
        CreateOrderResponseModel r_status = new CreateOrderResponseModel(null, "PAID", null, null);
        CreateOrderResponseModel r_msg = new CreateOrderResponseModel(null, null, "Success", null);
        CreateOrderResponseModel r_txn = new CreateOrderResponseModel(null, null, null, "txn-999");

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());

        assertNotEquals(r1, r_id);
        assertNotEquals(r_id, r1);
        assertNotEquals(r1, r_status);
        assertNotEquals(r_status, r1);
        assertNotEquals(r1, r_msg);
        assertNotEquals(r_msg, r1);
        assertNotEquals(r1, r_txn);
        assertNotEquals(r_txn, r1);

        CreateOrderResponseModel subclass = new CreateOrderResponseModel() {
            @Override
            public boolean canEqual(Object other) {
                return false;
            }
        };
        assertNotEquals(r1, subclass);
    }

    @Test
    void testToString() {
        CreateOrderResponseModel model = new CreateOrderResponseModel("ord-123", "PAID", "Success", "txn-999");
        String str = model.toString();
        assertNotNull(str);
        assertTrue(str.contains("orderId=ord-123"));
        assertTrue(str.contains("status=PAID"));
        assertTrue(str.contains("message=Success"));
        assertTrue(str.contains("paymentTransactionId=txn-999"));
    }
}
