package com.etorres.ecommerce.payment;

import com.etorres.ecommerce.customer.CustomerResponse;
import com.etorres.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
