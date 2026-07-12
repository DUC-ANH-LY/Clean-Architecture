package com.example.ecommerce.presenters.quarkus;

import com.example.ecommerce.core.usecase.CreateOrderResponseModel;
import com.example.ecommerce.core.usecase.CreateOrderUseCase;
import com.example.ecommerce.presenters.quarkus.dto.OrderRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Pure Quarkus REST resource using standard Jakarta REST / JAX-RS.
 */
@Path("/api/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    CreateOrderUseCase createOrderUseCase;

    @POST
    public Response createOrder(OrderRequest request) {
        try {
            CreateOrderResponseModel response = createOrderUseCase.execute(request.toRequestModel());
            return Response.ok(response).build();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ex.getMessage())
                    .build();
        }
    }
}
