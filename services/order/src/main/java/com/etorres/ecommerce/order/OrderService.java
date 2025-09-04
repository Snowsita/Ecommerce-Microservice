package com.etorres.ecommerce.order;

import com.etorres.ecommerce.customer.CustomerClient;
import com.etorres.ecommerce.exceptions.BusinessException;
import com.etorres.ecommerce.product.ProductClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient client;
    private final ProductClient productClient;

    public Integer createdOrder(@Valid OrderRequest request) {
        var customer = this.client.findCustomerById(request.customerId()).orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the ID: " + request.customerId()));

        // Purchase the products -> product-ms

        // Persist the order

        // Persist the order lines

        // Start payment process

        // Send order confirmation -> notification-ms (kafka)
        return null;
    }
}
