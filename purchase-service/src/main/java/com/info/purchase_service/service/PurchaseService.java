package com.info.purchase_service.service;

import com.info.purchase_service.dto.CheckoutRequestDTO;
import com.info.purchase_service.dto.PaymentVerificationRequestDTO;
import com.info.purchase_service.dto.PurchaseResponseDTO;
import com.info.purchase_service.entity.PurchaseOrder;
import com.info.purchase_service.exception.BadRequestException;
import com.info.purchase_service.exception.PaymentFailedException;
import com.info.purchase_service.exception.ResourceNotFoundException;
import com.info.purchase_service.payment.PaymentGatewayService;
import com.info.purchase_service.payment.PaymentResult;
import com.info.purchase_service.repo.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository repo;

    @Autowired
    private PaymentGatewayService paymentGateway;

    public PurchaseResponseDTO checkout(CheckoutRequestDTO req, Long userId) {
        if (req.getAmount() == null || req.getAmount() <= 0) {
            throw new BadRequestException("Purchase amount must be greater than zero");
        }

        if (repo.existsByUserIdAndCourseIdAndStatus(userId, req.getCourseId(), "COMPLETED")) {
            throw new BadRequestException("You have already purchased this course");
        }

        PurchaseOrder order = new PurchaseOrder();
        order.setUserId(userId);
        order.setCourseId(req.getCourseId());
        order.setAmount(req.getAmount());
        order.setCurrency(req.getCurrency());
        order.setPaymentMethod(req.getPaymentMethod());
        order.setStatus("PENDING");

        PurchaseOrder saved = repo.save(order);

        // Initiate dummy / test payment with gateway
        PaymentResult result = paymentGateway.initiatePayment(saved.getId(), req.getAmount(), req.getCurrency());
        saved.setTransactionId(result.getTransactionId());
        saved.setGatewayResponse(result.getGatewayMessage());
        saved = repo.save(saved);

        PurchaseResponseDTO dto = map(saved);
        dto.setMessage("Checkout initiated. Please verify payment with transactionId: " + result.getTransactionId());
        return dto;
    }

    public PurchaseResponseDTO verifyPayment(PaymentVerificationRequestDTO req, Long userId) {
        PurchaseOrder order = repo.findById(req.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Purchase order not found with ID: " + req.getOrderId()));

        if (!order.getUserId().equals(userId)) {
            throw new BadRequestException("You are not authorized to verify this payment");
        }

        if ("COMPLETED".equalsIgnoreCase(order.getStatus())) {
            PurchaseResponseDTO dto = map(order);
            dto.setMessage("Order is already completed");
            return dto;
        }

        PaymentResult result = paymentGateway.verifyPayment(req.getTransactionId(), req.getSimulateStatus());

        if (result.isSuccessful()) {
            order.setStatus("COMPLETED");
            order.setGatewayResponse(result.getGatewayMessage());
            order.setTransactionId(req.getTransactionId());
            PurchaseOrder updated = repo.save(order);
            PurchaseResponseDTO dto = map(updated);
            dto.setMessage("Payment successful. Course unlocked!");
            return dto;
        } else {
            order.setStatus("FAILED");
            order.setGatewayResponse(result.getGatewayMessage());
            repo.save(order);
            throw new PaymentFailedException("Payment verification failed: " + result.getGatewayMessage());
        }
    }

    public List<PurchaseResponseDTO> getMyPurchases(Long userId) {
        return repo.findByUserId(userId).stream()
                .map(this::map)
                .toList();
    }

    public PurchaseResponseDTO getById(Long id, Long userId, String role) {
        PurchaseOrder order = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase order not found with ID: " + id));

        if (!"ADMIN".equalsIgnoreCase(role) && !order.getUserId().equals(userId)) {
            throw new BadRequestException("Access denied to this order");
        }

        return map(order);
    }

    public List<PurchaseResponseDTO> getAllPurchases() {
        return repo.findAll().stream()
                .map(this::map)
                .toList();
    }

    private PurchaseResponseDTO map(PurchaseOrder order) {
        PurchaseResponseDTO dto = new PurchaseResponseDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setCourseId(order.getCourseId());
        dto.setAmount(order.getAmount());
        dto.setCurrency(order.getCurrency());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setTransactionId(order.getTransactionId());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(order.getCreatedAt());
        return dto;
    }
}
