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

    private Long parseUserId(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new BadRequestException("Authentication required: missing X-USER-ID header");
        }
        try {
            return Long.parseLong(userId.trim());
        } catch (NumberFormatException e) {
            long hash = Math.abs((long) userId.trim().hashCode());
            return hash == 0 ? 1L : hash;
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<PurchaseResponseDTO> checkout(
            @RequestHeader(value = "X-USER-ID", required = false) String userId,
            @RequestBody CheckoutRequestDTO request) {

        PurchaseResponseDTO response = service.checkout(request, parseUserId(userId));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<PurchaseResponseDTO> verifyPayment(
            @RequestHeader(value = "X-USER-ID", required = false) String userId,
            @RequestBody PaymentVerificationRequestDTO request) {

        PurchaseResponseDTO response = service.verifyPayment(request, parseUserId(userId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-purchases")
    public ResponseEntity<List<PurchaseResponseDTO>> getMyPurchases(
            @RequestHeader(value = "X-USER-ID", required = false) String userId) {

        return ResponseEntity.ok(service.getMyPurchases(parseUserId(userId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponseDTO> getById(
            @PathVariable Long id,
            @RequestHeader(value = "X-USER-ID", required = false) String userId,
            @RequestHeader(value = "X-ROLE", required = false) String role) {

        return ResponseEntity.ok(service.getById(id, parseUserId(userId), role));
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
