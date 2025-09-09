package com.etorres.ecommerce.order;

import com.etorres.ecommerce.customer.CustomerClient;
import com.etorres.ecommerce.exceptions.BusinessException;
import com.etorres.ecommerce.kafka.OrderConfirmation;
import com.etorres.ecommerce.kafka.OrderProducer;
import com.etorres.ecommerce.orderline.OrderLineRequest;
import com.etorres.ecommerce.orderline.OrderLineService;
import com.etorres.ecommerce.payment.PaymentClient;
import com.etorres.ecommerce.payment.PaymentRequest;
import com.etorres.ecommerce.product.ProductClient;
import com.etorres.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final CustomerClient client;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createdOrder(@Valid OrderRequest request) {
        var customer = this.client.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the ID: " + request.customerId()));

        var purchasedProducts = this.productClient.purchaseProducts(request.products());

        var order = this.repository.save(mapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(new OrderLineRequest(null, order.getId(), purchaseRequest.productId(), purchaseRequest.quantity()));
        }

        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );

        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll().stream().map(mapper::toOrderResponse).toList();
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::toOrderResponse)
                .orElseThrow(() -> new EntityNotFoundException("No order found with the ID: " + orderId));
    }
}
