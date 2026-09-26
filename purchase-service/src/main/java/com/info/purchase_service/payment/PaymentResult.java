package com.info.purchase_service.payment;

public class PaymentResult {
    private boolean successful;
    private String transactionId;
    private String gatewayMessage;

    public PaymentResult(boolean successful, String transactionId, String gatewayMessage) {
        this.successful = successful;
        this.transactionId = transactionId;
        this.gatewayMessage = gatewayMessage;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getGatewayMessage() {
        return gatewayMessage;
    }
}
