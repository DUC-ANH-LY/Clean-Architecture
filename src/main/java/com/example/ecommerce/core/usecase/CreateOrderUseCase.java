package com.example.ecommerce.core.usecase;

import com.example.ecommerce.core.entity.Order;
import com.example.ecommerce.core.entity.OrderItem;
import com.example.ecommerce.core.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * APPLICATION USE CASE — Pure Java class.
 *
 * This class contains the core business orchestration for creating an order:
 *   1. Fetch each product
 *   2. Check stock availability
 *   3. Process payment via PaymentServiceI
 *   4. Reduce stock
 *   5. Persist the order via OrderRepoI
 *
 * It depends ONLY on the core interfaces (ProductRepoI, InventoryRepoI,
 * PaymentServiceI, OrderRepoI). It has zero knowledge of Spring, JPA,
 * MongoDB, Stripe, or VNPay — those are infra concerns.
 */
public class CreateOrderUseCase {

    private final ProductRepoI productRepo;
    private final InventoryRepoI inventoryRepo;
    private final PaymentServiceI paymentService;
    private final OrderRepoI orderRepo;

    public CreateOrderUseCase(ProductRepoI productRepo,
                              InventoryRepoI inventoryRepo,
                              PaymentServiceI paymentService,
                              OrderRepoI orderRepo) {
        this.productRepo = productRepo;
        this.inventoryRepo = inventoryRepo;
        this.paymentService = paymentService;
        this.orderRepo = orderRepo;
    }

    public CreateOrderResponseModel execute(CreateOrderRequestModel request) {
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        // Step 1 & 2: Fetch products and verify stock
        for (CreateOrderRequestModel.OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepo.findById(itemRequest.productId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Product not found: " + itemRequest.productId()));

            if (!inventoryRepo.isStockAvailable(itemRequest.productId(), itemRequest.quantity())) {
                throw new IllegalStateException(
                        "Insufficient stock for product: " + itemRequest.productId());
            }

            BigDecimal itemTotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.quantity()));
            totalAmount = totalAmount.add(itemTotal);

            orderItems.add(new OrderItem(
                    itemRequest.productId(),
                    itemRequest.quantity(),
                    product.getPrice()
            ));
        }

        // Step 3: Process payment
        String paymentTransactionId = paymentService.processPayment(
                request.getCustomerId(),
                totalAmount,
                request.getPaymentToken()
        );

        // Step 4: Reduce stock after successful payment
        for (CreateOrderRequestModel.OrderItemRequest itemRequest : request.getItems()) {
            inventoryRepo.reduceStock(itemRequest.productId(), itemRequest.quantity());
        }

        // Step 5: Create and persist the order
        Order order = new Order(
                null,
                request.getCustomerId(),
                orderItems,
                totalAmount,
                Order.OrderStatus.PAID,
                LocalDateTime.now()
        );

        Order savedOrder = orderRepo.save(order);

        return new CreateOrderResponseModel(
                savedOrder.getId(),
                savedOrder.getStatus().name(),
                "Order created successfully",
                paymentTransactionId
        );
    }
}
