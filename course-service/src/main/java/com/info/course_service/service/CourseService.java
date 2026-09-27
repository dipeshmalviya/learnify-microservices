package com.info.course_service.service;

import com.info.course_service.dto.CourseRequestDTO;
import com.info.course_service.dto.CourseResponseDTO;
import com.info.course_service.entity.Course;
import com.info.course_service.exception.BadRequestException;
import com.info.course_service.exception.ResourceNotFoundException;
import com.info.course_service.repo.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository repo;

    public CourseResponseDTO createCourse(CourseRequestDTO req, Long instructorId) {
        if (req.getTitle() == null || req.getTitle().isBlank()) {
            throw new BadRequestException("Course title cannot be empty");
        }
        if (req.getPrice() == null || req.getPrice() < 0) {
            throw new BadRequestException("Price must be a non-negative number");
        }

        Course course = new Course();
        course.setTitle(req.getTitle().trim());
        course.setDescription(req.getDescription());
        course.setPrice(req.getPrice());
        course.setCategoryId(req.getCategoryId());
        course.setLevel(req.getLevel());
        course.setInstructorId(instructorId);
        course.setStatus("PUBLISHED");

        Course saved = repo.save(course);
        return map(saved);
    }

    public List<CourseResponseDTO> getAllCourses() {
        return repo.findAll().stream()
                .map(this::map)
                .toList();
    }

    public CourseResponseDTO getById(Long id) {
        Course course = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        return map(course);
    }

    public List<CourseResponseDTO> getByCategory(Long categoryId) {
        return repo.findByCategoryId(categoryId).stream()
                .map(this::map)
                .toList();
    }

    private CourseResponseDTO map(Course c) {
        CourseResponseDTO dto = new CourseResponseDTO();
        dto.setId(c.getId());
        dto.setTitle(c.getTitle());
        dto.setDescription(c.getDescription());
        dto.setPrice(c.getPrice());
        dto.setInstructorId(c.getInstructorId());
        dto.setCategoryId(c.getCategoryId());
        dto.setLevel(c.getLevel());
        dto.setStatus(c.getStatus());
        dto.setCreatedAt(c.getCreatedAt());
        return dto;
    }
}
