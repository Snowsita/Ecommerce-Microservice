package com.etorres.ecommerce.kafka;

import com.etorres.ecommerce.kafka.order.OrderConfirmation;
import com.etorres.ecommerce.kafka.payment.PaymentConfirmation;
import com.etorres.ecommerce.notification.Notification;
import com.etorres.ecommerce.notification.NotificationRepository;
import com.etorres.ecommerce.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository repository;
    // private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) {
        log.info("Consuming the message from payment-topic: Topic:: {}", paymentConfirmation);
        repository.save(Notification.builder()
                .type(NotificationType.PAYMENT_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .paymentConfirmation(paymentConfirmation)
                .build());

        // TODO Send Email
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) {
        log.info("Consuming the message from payment-topic: Topic:: {}", orderConfirmation);
        repository.save(Notification.builder()
                .type(NotificationType.ORDER_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .orderConfirmation(orderConfirmation)
                .build());

        // TODO Send Email
    }
}
