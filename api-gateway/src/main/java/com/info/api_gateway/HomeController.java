package com.info.api_gateway;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> home() {
        return ResponseEntity.ok(Map.of(
                "status", "ONLINE",
                "service", "Learnify API Gateway",
                "message", "Learnify Microservices Gateway is live and operational!",
                "endpoints", Map.of(
                        "register", "POST /auth",
                        "signin", "POST /auth/signin",
                        "categories", "GET /categories",
                        "checkout", "POST /purchases/checkout",
                        "verify_payment", "POST /purchases/verify"
                )
        ));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }
}
