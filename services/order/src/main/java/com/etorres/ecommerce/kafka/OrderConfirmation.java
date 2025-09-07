package com.etorres.ecommerce.kafka;

import com.etorres.ecommerce.customer.CustomerResponse;
import com.etorres.ecommerce.order.PaymentMethod;
import com.etorres.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
