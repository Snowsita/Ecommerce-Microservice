package com.etorres.ecommerce.order;

import jakarta.validation.constraints.Positive;

public record OrderLineRequest(Integer id, Integer orderId, Integer productId,
                               @Positive(message = "Quantity is required") double quantity) {
}
