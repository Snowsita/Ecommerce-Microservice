package com.etorres.ecommerce.orderline;

import jakarta.validation.constraints.Positive;

public record OrderLineRequest(Integer id, Integer orderId, Integer productId,
                               @Positive(message = "Quantity is required") double quantity) {
}
