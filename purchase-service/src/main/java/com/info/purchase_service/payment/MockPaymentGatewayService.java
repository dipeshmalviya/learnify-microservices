package com.info.purchase_service.payment;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MockPaymentGatewayService implements PaymentGatewayService {

    @Override
    public PaymentResult initiatePayment(Long orderId, Double amount, String currency) {
        // Generates dummy secure transaction token for checkout
        String transactionId = "TXN_MOCK_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
        return new PaymentResult(true, transactionId, "Mock payment order created successfully for amount " + amount + " " + currency);
    }

    @Override
    public PaymentResult verifyPayment(String transactionId, String simulateStatus) {
        // Supports simulating both success and failed payment scenarios for testing
        if ("FAILED".equalsIgnoreCase(simulateStatus)) {
            return new PaymentResult(false, transactionId, "Simulated payment failure (insufficient funds or bank declined)");
        }
        return new PaymentResult(true, transactionId, "Payment verified successfully by mock gateway");
    }
}
