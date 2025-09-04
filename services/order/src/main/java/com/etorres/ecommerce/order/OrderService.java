package com.etorres.ecommerce.order;

import com.etorres.ecommerce.customer.CustomerClient;
import com.etorres.ecommerce.exceptions.BusinessException;
import com.etorres.ecommerce.orderline.OrderLineService;
import com.etorres.ecommerce.product.ProductClient;
import com.etorres.ecommerce.product.PurchaseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final CustomerClient client;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;

    public Integer createdOrder(@Valid OrderRequest request) {
        var customer = this.client.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the ID: " + request.customerId()));

        this.productClient.purchaseProducts(request.products());

        var order = this.repository.save(mapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(new OrderLineRequest(null, order.getId(), purchaseRequest.productId(), purchaseRequest.quantity()));
        }

        // Start payment process

        // Send order confirmation -> notification-ms (kafka)
        return null;
    }
}
