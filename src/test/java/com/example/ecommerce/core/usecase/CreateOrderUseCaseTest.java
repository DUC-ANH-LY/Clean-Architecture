package com.example.ecommerce.core.usecase;

import com.example.ecommerce.core.entity.Order;
import com.example.ecommerce.core.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseTest {

    @Mock
    private ProductRepoI productRepo;

    @Mock
    private InventoryRepoI inventoryRepo;

    @Mock
    private PaymentServiceI paymentService;

    @Mock
    private OrderRepoI orderRepo;

    @InjectMocks
    private CreateOrderUseCase useCase;

    @Test
    void testExecute_Success() {
        // Arrange
        String customerId = "cust-123";
        String paymentToken = "stripe-tok-999";
        String productId1 = "p-1";
        String productId2 = "p-2";

        CreateOrderRequestModel.OrderItemRequest item1 = new CreateOrderRequestModel.OrderItemRequest(productId1, 2);
        CreateOrderRequestModel.OrderItemRequest item2 = new CreateOrderRequestModel.OrderItemRequest(productId2, 1);
        CreateOrderRequestModel request = new CreateOrderRequestModel(customerId, Arrays.asList(item1, item2), paymentToken);

        Product product1 = new Product(productId1, "Product 1", new BigDecimal("10.00"), 100);
        Product product2 = new Product(productId2, "Product 2", new BigDecimal("25.00"), 50);

        when(productRepo.findById(productId1)).thenReturn(Optional.of(product1));
        when(productRepo.findById(productId2)).thenReturn(Optional.of(product2));

        when(inventoryRepo.isStockAvailable(productId1, 2)).thenReturn(true);
        when(inventoryRepo.isStockAvailable(productId2, 1)).thenReturn(true);

        // totalAmount = (10.00 * 2) + (25.00 * 1) = 45.00
        BigDecimal expectedTotal = new BigDecimal("45.00");
        String txnId = "txn-555";
        when(paymentService.processPayment(customerId, expectedTotal, paymentToken)).thenReturn(txnId);

        Order savedOrder = new Order();
        savedOrder.setId("ord-888");
        savedOrder.setStatus(Order.OrderStatus.PAID);
        when(orderRepo.save(any(Order.class))).thenReturn(savedOrder);

        // Act
        CreateOrderResponseModel response = useCase.execute(request);

        // Assert
        assertNotNull(response);
        assertEquals("ord-888", response.getOrderId());
        assertEquals("PAID", response.getStatus());
        assertEquals("Order created successfully", response.getMessage());
        assertEquals(txnId, response.getPaymentTransactionId());

        // Verifications
        verify(productRepo).findById(productId1);
        verify(productRepo).findById(productId2);
        verify(inventoryRepo).isStockAvailable(productId1, 2);
        verify(inventoryRepo).isStockAvailable(productId2, 1);
        verify(paymentService).processPayment(customerId, expectedTotal, paymentToken);

        // Verify stock was reduced
        verify(inventoryRepo).reduceStock(productId1, 2);
        verify(inventoryRepo).reduceStock(productId2, 1);

        // Verify order saved with correct properties
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepo).save(orderCaptor.capture());
        Order capturedOrder = orderCaptor.getValue();
        assertNull(capturedOrder.getId());
        assertEquals(customerId, capturedOrder.getCustomerId());
        assertEquals(2, capturedOrder.getItems().size());
        assertEquals(expectedTotal, capturedOrder.getTotalAmount());
        assertEquals(Order.OrderStatus.PAID, capturedOrder.getStatus());
        assertNotNull(capturedOrder.getCreatedAt());
    }

    @Test
    void testExecute_ProductNotFound() {
        // Arrange
        String productId = "p-nonexistent";
        CreateOrderRequestModel.OrderItemRequest item = new CreateOrderRequestModel.OrderItemRequest(productId, 1);
        CreateOrderRequestModel request = new CreateOrderRequestModel("cust-123", Collections.singletonList(item), "token");

        when(productRepo.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(request);
        });
        assertEquals("Product not found: " + productId, exception.getMessage());

        // Verify subsequent services and repos are not called
        verify(inventoryRepo, never()).isStockAvailable(anyString(), anyInt());
        verify(paymentService, never()).processPayment(anyString(), any(BigDecimal.class), anyString());
        verify(inventoryRepo, never()).reduceStock(anyString(), anyInt());
        verify(orderRepo, never()).save(any(Order.class));
    }

    @Test
    void testExecute_InsufficientStock() {
        // Arrange
        String productId = "p-1";
        CreateOrderRequestModel.OrderItemRequest item = new CreateOrderRequestModel.OrderItemRequest(productId, 5);
        CreateOrderRequestModel request = new CreateOrderRequestModel("cust-123", Collections.singletonList(item), "token");

        Product product = new Product(productId, "Product 1", new BigDecimal("10.00"), 2);
        when(productRepo.findById(productId)).thenReturn(Optional.of(product));
        when(inventoryRepo.isStockAvailable(productId, 5)).thenReturn(false);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            useCase.execute(request);
        });
        assertEquals("Insufficient stock for product: " + productId, exception.getMessage());

        // Verify subsequent services and repos are not called
        verify(paymentService, never()).processPayment(anyString(), any(BigDecimal.class), anyString());
        verify(inventoryRepo, never()).reduceStock(anyString(), anyInt());
        verify(orderRepo, never()).save(any(Order.class));
    }

    @Test
    void testExecute_PaymentFailure() {
        // Arrange
        String customerId = "cust-123";
        String paymentToken = "invalid-token";
        String productId = "p-1";

        CreateOrderRequestModel.OrderItemRequest item = new CreateOrderRequestModel.OrderItemRequest(productId, 1);
        CreateOrderRequestModel request = new CreateOrderRequestModel(customerId, Collections.singletonList(item), paymentToken);

        Product product = new Product(productId, "Product 1", new BigDecimal("10.00"), 10);
        when(productRepo.findById(productId)).thenReturn(Optional.of(product));
        when(inventoryRepo.isStockAvailable(productId, 1)).thenReturn(true);

        when(paymentService.processPayment(eq(customerId), any(BigDecimal.class), eq(paymentToken)))
                .thenThrow(new RuntimeException("Payment processing failed"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            useCase.execute(request);
        });
        assertEquals("Payment processing failed", exception.getMessage());

        // Verify stock reduce and save order are NEVER called
        verify(inventoryRepo, never()).reduceStock(anyString(), anyInt());
        verify(orderRepo, never()).save(any(Order.class));
    }
}
