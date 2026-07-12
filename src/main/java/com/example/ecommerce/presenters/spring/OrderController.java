package com.example.ecommerce.presenters.spring;

import com.example.ecommerce.core.usecase.CreateOrderResponseModel;
import com.example.ecommerce.core.usecase.CreateOrderUseCase;
import com.example.ecommerce.presenters.spring.dto.OrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Spring MVC REST Controller — Inbound Presenter.
 *
 * Responsibilities:
 *  - Receive HTTP POST /api/spring/orders
 *  - Map JSON body → CreateOrderRequestModel
 *  - Call CreateOrderUseCase
 *  - Map CreateOrderResponseModel → HTTP response
 *
 * Knows about Spring (@RestController) but NOT about infra (JPA, Stripe, etc.)
 */
@RestController
@RequestMapping("/api/spring/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<CreateOrderResponseModel> createOrder(
            @RequestBody OrderRequest request) {
        CreateOrderResponseModel response = createOrderUseCase.execute(request.toRequestModel());
        return ResponseEntity.ok(response);
    }

    // Global exception handler for use case business exceptions
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<String> handleBusinessException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
