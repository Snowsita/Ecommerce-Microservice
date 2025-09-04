package com.etorres.ecommerce.order;

import com.etorres.ecommerce.product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,

        @Positive(message = "Order amount must be positive")
        BigDecimal amount,

        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,

        @NotNull(message = "Customer is required")
        @NotEmpty(message = "Customer is required")
        @NotBlank(message = "Customer is required")
        String customerId,

        @NotEmpty(message = "At least one product is required")
        List<PurchaseRequest> products
) {
}
