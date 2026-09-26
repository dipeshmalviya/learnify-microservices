package com.info.purchase_service.dto;

public class PaymentVerificationRequestDTO {
    private Long orderId;
    private String transactionId;
    private String simulateStatus; // "SUCCESS" or "FAILED" (optional, defaults to SUCCESS)

    public PaymentVerificationRequestDTO() {}

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSimulateStatus() {
        return simulateStatus == null ? "SUCCESS" : simulateStatus;
    }

    public void setSimulateStatus(String simulateStatus) {
        this.simulateStatus = simulateStatus;
    }
}
