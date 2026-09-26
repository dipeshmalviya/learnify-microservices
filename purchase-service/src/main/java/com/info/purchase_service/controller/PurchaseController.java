package com.info.purchase_service.controller;

import com.info.purchase_service.dto.CheckoutRequestDTO;
import com.info.purchase_service.dto.PaymentVerificationRequestDTO;
import com.info.purchase_service.dto.PurchaseResponseDTO;
import com.info.purchase_service.exception.BadRequestException;
import com.info.purchase_service.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService service;

    @PostMapping("/checkout")
    public ResponseEntity<PurchaseResponseDTO> checkout(
            @RequestHeader(value = "X-USER-ID", required = false) String userId,
            @RequestBody CheckoutRequestDTO request) {

        if (userId == null || userId.isBlank()) {
            throw new BadRequestException("Authentication required: missing X-USER-ID header");
        }

        PurchaseResponseDTO response = service.checkout(request, Long.valueOf(userId));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<PurchaseResponseDTO> verifyPayment(
            @RequestHeader(value = "X-USER-ID", required = false) String userId,
            @RequestBody PaymentVerificationRequestDTO request) {

        if (userId == null || userId.isBlank()) {
            throw new BadRequestException("Authentication required: missing X-USER-ID header");
        }

        PurchaseResponseDTO response = service.verifyPayment(request, Long.valueOf(userId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-purchases")
    public ResponseEntity<List<PurchaseResponseDTO>> getMyPurchases(
            @RequestHeader(value = "X-USER-ID", required = false) String userId) {

        if (userId == null || userId.isBlank()) {
            throw new BadRequestException("Authentication required: missing X-USER-ID header");
        }

        return ResponseEntity.ok(service.getMyPurchases(Long.valueOf(userId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponseDTO> getById(
            @PathVariable Long id,
            @RequestHeader(value = "X-USER-ID", required = false) String userId,
            @RequestHeader(value = "X-ROLE", required = false) String role) {

        if (userId == null || userId.isBlank()) {
            throw new BadRequestException("Authentication required: missing X-USER-ID header");
        }

        return ResponseEntity.ok(service.getById(id, Long.valueOf(userId), role));
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<PurchaseResponseDTO>> getAllPurchases(
            @RequestHeader(value = "X-ROLE", required = false) String role) {

        if (!"ADMIN".equalsIgnoreCase(role)) {
            throw new BadRequestException("Admin access required");
        }

        return ResponseEntity.ok(service.getAllPurchases());
    }
}
