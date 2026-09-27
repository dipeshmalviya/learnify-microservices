package com.info.course_service.controller;

import com.info.course_service.dto.CourseRequestDTO;
import com.info.course_service.dto.CourseResponseDTO;
import com.info.course_service.exception.BadRequestException;
import com.info.course_service.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService service;

    @PostMapping
    public ResponseEntity<CourseResponseDTO> create(
            @RequestHeader(value = "X-USER-ID", required = false) String userId,
            @RequestHeader(value = "X-ROLE", required = false) String role,
            @RequestBody CourseRequestDTO request) {

        if (userId == null || userId.isBlank()) {
            throw new BadRequestException("Authentication required: missing X-USER-ID header");
        }

        if (!"ADMIN".equalsIgnoreCase(role) && !"MENTOR".equalsIgnoreCase(role)) {
            throw new BadRequestException("Only ADMIN or MENTOR can create courses");
        }

        CourseResponseDTO created = service.createCourse(request, Long.valueOf(userId));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<CourseResponseDTO>> getByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(service.getByCategory(categoryId));
    }
}
