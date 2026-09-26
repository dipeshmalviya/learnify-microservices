package com.info.purchase_service.dto;

public class CheckoutRequestDTO {
    private Long courseId;
    private Double amount;
    private String currency; // default "USD"
    private String paymentMethod; // "CREDIT_CARD", "UPI", "DUMMY_GATEWAY"

    public CheckoutRequestDTO() {}

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency == null ? "USD" : currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentMethod() {
        return paymentMethod == null ? "DUMMY_GATEWAY" : paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
