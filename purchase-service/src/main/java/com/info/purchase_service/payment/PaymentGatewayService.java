package com.info.purchase_service.payment;

public interface PaymentGatewayService {
    PaymentResult initiatePayment(Long orderId, Double amount, String currency);
    PaymentResult verifyPayment(String transactionId, String simulateStatus);
}
