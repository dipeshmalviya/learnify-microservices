package com.info.category_service.controller;

import com.info.category_service.dto.CategoryResponseDTO;
import com.info.category_service.entity.Category;
import com.info.category_service.exception.UnauthorizedException;
import com.info.category_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(
            @RequestHeader("X-USER-ID") String userId,
            @RequestHeader("X-ROLE") String role,
            @RequestBody Category category) {
        if (!"ADMIN".equals(role)) {
            throw new UnauthorizedException("Only admin can create category");
        }
        CategoryResponseDTO created = service.create(category, Long.valueOf(userId));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
}