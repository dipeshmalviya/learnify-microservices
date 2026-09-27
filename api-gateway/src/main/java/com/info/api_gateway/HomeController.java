package com.info.api_gateway;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public Mono<String> home() {
        return Mono.fromCallable(() -> {
            Resource resource = new ClassPathResource("static/index.html");
            try (InputStream is = resource.getInputStream()) {
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
        });
    }

    @GetMapping("/api/info")
    public ResponseEntity<Map<String, Object>> apiInfo() {
        return ResponseEntity.ok(Map.of(
                "status", "ONLINE",
                "service", "Learnify API Gateway",
                "message", "Learnify Microservices Gateway is live and operational!",
                "endpoints", Map.of(
                        "register", "POST /auth",
                        "signin", "POST /auth/signin",
                        "categories", "GET /categories",
                        "courses", "GET /courses",
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
